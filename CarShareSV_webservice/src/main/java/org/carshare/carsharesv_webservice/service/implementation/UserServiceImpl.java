package org.carshare.carsharesv_webservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.response.RoleResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Role;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.exception.*;
import org.carshare.carsharesv_webservice.repository.iRoleRepository;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.carshare.carsharesv_webservice.service.iUserService;
import org.carshare.carsharesv_webservice.util.Constants;
import org.carshare.carsharesv_webservice.util.CurrentUserInfo;
import org.carshare.carsharesv_webservice.util.UsefullMethods;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements iUserService {
    private final iUserRepository userRepository;
    private final iRoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UsefullMethods usefullMethods;

    // get all users, active or not active
    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> users = userRepository.findAll().stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();

        // check if users is empty
        if(users.isEmpty()) throw new ResourceNotFoundException("No users found");

        return users;
    }

    // get all users, active
    @Override
    public List<UserResponseDTO> getAllActiveUsers() {
        List<UserResponseDTO> users = userRepository.findByActive(true).stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();

        // check if users is empty
        if(users.isEmpty()) throw new ResourceNotFoundException("No users found");

        return users;
    }

    // get all users, not active
    @Override
    public List<UserResponseDTO> getAllNotActiveUsers() {
        List<UserResponseDTO> users = userRepository.findByActive(false).stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();

        // check if users is empty
        if(users.isEmpty()) throw new ResourceNotFoundException("No users found");

        return users;
    }

    // only active user
    @Override
    public UserResponseDTO getUserById(UUID userId) {
        User user = userRepository.findOneByUserId(userId);

        if(user == null) throw new ResourceNotFoundException("User not found");
        if(!user.getActive()) throw new NotActiveUserException("User is not active");

        return modelMapper.map(user, UserResponseDTO.class);
    }

    // only active user
    @Override
    public UserResponseDTO getUserByUsernameOrEmail(String identifier) {
        User user = userRepository.findByUsernameOrEmail(identifier, identifier).orElse(null);

        if(user == null) throw new ResourceNotFoundException("User not found");
        if(!user.getActive()) throw new NotActiveUserException("User is not active");

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public List<RoleResponseDTO> getAllUserRoles(UUID userId) {
        User user = userRepository.findOneByUserId(userId);

        if(user == null) throw new ResourceNotFoundException("User not found");

        return user.getRoles().stream().map(role -> modelMapper.map(role, RoleResponseDTO.class)).toList();
    }

    @Override
    public void deactivateUser(UUID userId) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(userId);

        if(userInfo.requestedUser() == null) throw new ResourceNotFoundException("To deactivate User not found");
        if(!userInfo.requestedUser().getActive()) throw new NotActiveUserException("To deactivate User is not active already");
        if(userInfo.currentUser() == null) throw new ResourceNotFoundException("Current User not found");


        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || userInfo.currentUser().getUserId().equals(userInfo.requestedUser().getUserId())) {
            userInfo.requestedUser().setActive(false);
            userRepository.save(userInfo.requestedUser());
        } else throw new NotAllowedOperationException("You don't have permissions to deactivate this user. You can only deactivate your own account");
    }

    @Override
    public void activateUser(UUID userId) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(userId);

        if(userInfo.requestedUser() == null) throw new ResourceNotFoundException("To Activate User not found");
        if(userInfo.requestedUser().getActive() == true) throw new NotActiveUserException("To Activate User is active already");
        if(userInfo.currentUser() == null) throw new ResourceNotFoundException("Current User not found");

        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || userInfo.currentUser().getUserId().equals(userInfo.requestedUser().getUserId())) {
            userInfo.requestedUser().setActive(true);
            userRepository.save(userInfo.requestedUser());
        } else throw new NotAllowedOperationException("You don't have permissions to Activate this user. You can only Activate your own account");
    }

    //common method to update user field
    private UserResponseDTO updateUserField(UUID userId, String newValue, BiConsumer<User, String> updateFunction) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(userId);

        if(userInfo.requestedUser() == null) throw new ResourceNotFoundException("To Update User not found");
        if(userInfo.currentUser() == null) throw new ResourceNotFoundException("Current User not found");

        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || userInfo.currentUser().getUserId().equals(userInfo.requestedUser().getUserId())) {
            updateFunction.accept(userInfo.requestedUser(), newValue);
            userRepository.save(userInfo.requestedUser());
        } else throw new NotAllowedOperationException("You don't have permissions to update this user. You can only update your own account");

        return modelMapper.map(userInfo.requestedUser(), UserResponseDTO.class);
    }


    @Override
    public UserResponseDTO updateUserFirstName(UUID userId, String firstName) {
        return updateUserField(userId, firstName, User::setFirstName);
    }

    @Override
    public UserResponseDTO updateUserLastName(UUID userId, String lastName) {
        return updateUserField(userId, lastName, User::setLastName);
    }

    @Override
    public UserResponseDTO updateUserUsername(UUID userId, String username) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(userId);

        if(userInfo.requestedUser() == null) throw new ResourceNotFoundException("To Update User not found");
        if(userInfo.currentUser() == null) throw new ResourceNotFoundException("Current User not found");

        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || userInfo.currentUser().getUserId().equals(userInfo.requestedUser().getUserId())) {
            // Verify if the username is already in use
            if (userRepository.findByUsernameOrEmail(username, username).isPresent()) throw new UsernameAlreadyExistsException("Username already taken");

            // Save old username
            String oldUsername = userInfo.requestedUser().getUsername();

            // update username
            userInfo.requestedUser().setUsername(username);
            userRepository.save(userInfo.requestedUser());

            // if user is updating its own username
            if(userInfo.currentUser().getUserId().equals(userInfo.requestedUser().getUserId())) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                if (auth != null && auth.getName().equals(oldUsername)) {
                    // Force logout
                    SecurityContextHolder.clearContext();

                    throw new RequiresReauthenticationException("Username updated. Please login again with your new username.");
                }
            }

        } else throw new NotAllowedOperationException("You don't have permissions to update this user. You can only update your own account");

        return modelMapper.map(userInfo.requestedUser(), UserResponseDTO.class);
        //return updateUserField(userId, username, User::setUsername);
    }

    @Override
    public UserResponseDTO updateUserEmail(UUID userId, String email) {
        return updateUserField(userId, email, User::setEmail);
    }

    @Override
    public UserResponseDTO updateUserPhoneNumber(UUID userId, String phoneNumber) {
        return updateUserField(userId, phoneNumber, User::setPhoneNumber);
    }

    public void grantAdminRoleToUser(UUID userId) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(userId);
        User toGrantRoleUser = userInfo.requestedUser();

        if(toGrantRoleUser == null) throw new ResourceNotFoundException("To Grant Role User not found");
        if(userInfo.currentUser() == null) throw new ResourceNotFoundException("Current User not found");

        if(userInfo.roles().contains(Constants.SYSADMIN)) {
            Set<Role> currentRoles = toGrantRoleUser.getRoles();

            if (currentRoles.stream().anyMatch(role -> role.getRoleName().equals(Constants.ADMIN))) throw new RoleAlreadyAssignedException("ADMIN role is already assigned to " + toGrantRoleUser.getUsername());

            roleRepository.addRoleToUser(toGrantRoleUser.getUserId(), 2);

            userRepository.save(toGrantRoleUser);
        } else throw new NotAllowedOperationException("You don't have permissions to update this user. You can only update your own account");
    }

    public void revokeAdminRoleFromUser(UUID userId) {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(userId);
        User toRevokeRoleUser = userInfo.requestedUser();

        if(toRevokeRoleUser == null) throw new ResourceNotFoundException("To Grant Role User not found");
        if(userInfo.currentUser() == null) throw new ResourceNotFoundException("Current User not found");


        if(userInfo.roles().contains(Constants.SYSADMIN)) {
            Set<Role> currentRoles = toRevokeRoleUser.getRoles();

            if (currentRoles.stream().anyMatch(role -> role.getRoleName().equals(Constants.ADMIN))) {
                roleRepository.revokeRoleFromUser(toRevokeRoleUser.getUserId(), 2);

                userRepository.save(toRevokeRoleUser);
            } else throw new RoleNotAssignedException("ADMIN role is not assigned to " + toRevokeRoleUser.getUsername());
        } else throw new NotAllowedOperationException("You don't have permissions to update this user. You can only update your own account");
    }
}
