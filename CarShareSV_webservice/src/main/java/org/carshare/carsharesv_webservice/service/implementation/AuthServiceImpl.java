package org.carshare.carsharesv_webservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.create.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Role;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.exception.ExistingUserException;
import org.carshare.carsharesv_webservice.repository.iRoleRepository;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.carshare.carsharesv_webservice.service.iAuthService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static org.carshare.carsharesv_webservice.util.Constants.USER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements iAuthService {
    private final iUserRepository userRepository;
    private final iRoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO register(CreateUserDTO userDTO) throws Exception {
        User existingUser = userRepository.findByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail()).orElse(null);

        // check if user exists
        if(existingUser != null) throw new ExistingUserException("User already exists");

        // create new user
        User newUser = new User();
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setUsername(userDTO.getUsername());
        newUser.setBirthdate(userDTO.getBirthdate());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setEmail(userDTO.getEmail());
        newUser.setActive(true);

        // adding role to user
        Role userRole = roleRepository.findByRoleName(USER).orElseThrow(() -> new Exception("Role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);

        // save user
        userRepository.save(newUser);

        // return and map object
        return modelMapper.map(newUser, UserResponseDTO.class);
    }
}
