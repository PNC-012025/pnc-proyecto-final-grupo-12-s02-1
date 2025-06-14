package org.carshare.carsharesv_webservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.response.RoleResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.exception.NotActiveUserException;
import org.carshare.carsharesv_webservice.exception.NotAllowedOperationException;
import org.carshare.carsharesv_webservice.exception.ResourceNotFoundException;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.carshare.carsharesv_webservice.security.JwtProvider;
import org.carshare.carsharesv_webservice.service.iUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements iUserService {
    private final iUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtProvider tokenProvider;

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
        String token = tokenProvider.getCurrentToken();
        String username = tokenProvider.getUsernameFromToken(token);
        List<String> roles = tokenProvider.getRolesFromToken(token);
        User currentUser = userRepository.findByUsernameOrEmail(username, username).orElse(null);
        User toDeactivateUser = userRepository.findOneByUserId(userId);


        if(toDeactivateUser == null) throw new ResourceNotFoundException("To deactivate User not found");
        if(!toDeactivateUser.getActive()) throw new NotActiveUserException("To deactivate User is not active already");
        if(currentUser == null) throw new ResourceNotFoundException("Current User not found");


        if(roles.contains("ADMIN") || currentUser.getUserId().equals(toDeactivateUser.getUserId())) {
            toDeactivateUser.setActive(false);
            userRepository.save(toDeactivateUser);
        } else throw new NotAllowedOperationException("You don't have permissions to deactivate this user. You can only deactivate your own account");
    }

    @Override
    public void activateUser(UUID userId) {
        String token = tokenProvider.getCurrentToken();
        String username = tokenProvider.getUsernameFromToken(token);
        List<String> roles = tokenProvider.getRolesFromToken(token);
        User currentUser = userRepository.findByUsernameOrEmail(username, username).orElse(null);
        User toActivateUser = userRepository.findOneByUserId(userId);


        if(toActivateUser == null) throw new ResourceNotFoundException("To Activate User not found");
        if(!toActivateUser.getActive()) throw new NotActiveUserException("To Activate User is active already");
        if(currentUser == null) throw new ResourceNotFoundException("Current User not found");


        if(roles.contains("ADMIN") || currentUser.getUserId().equals(toActivateUser.getUserId())) {
            toActivateUser.setActive(true);
            userRepository.save(toActivateUser);
        } else throw new NotAllowedOperationException("You don't have permissions to Activate this user. You can only Activate your own account");
    }
}
