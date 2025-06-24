package org.carshare.carsharesv_webservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.request.LoginRequestDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.WhoamiResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Role;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.exception.ExistingUserException;
import org.carshare.carsharesv_webservice.repository.iRoleRepository;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.carshare.carsharesv_webservice.security.JwtProvider;
import org.carshare.carsharesv_webservice.service.iAuthService;
import org.carshare.carsharesv_webservice.util.CurrentUserInfo;
import org.carshare.carsharesv_webservice.util.UsefullMethods;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.carshare.carsharesv_webservice.util.Constants.USER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements iAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final iUserRepository userRepository;
    private final iRoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UsefullMethods usefullMethods;

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
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        // Authenticates the user using the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        // Sets the authentication in Spring Security's context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generates a JWT token for the authenticated user
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public WhoamiResponseDTO whoami() {
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);

        User currentUser = userInfo.currentUser();
        List<String> roles = userInfo.roles();

        WhoamiResponseDTO response = modelMapper.map(currentUser, WhoamiResponseDTO.class);
        response.setRoles(roles);

        return response;
    }
}
