package org.carshare.carsharesv_webservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.create.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.exception.ExistingUserException;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.carshare.carsharesv_webservice.service.iUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements iUserService {
    private final iUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO register(CreateUserDTO userDTO) {
        User existingUser = userRepository.findByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail()).orElse(null);

        if(existingUser != null) throw new ExistingUserException("User already exists");

        User newUser = new User();
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setUsername(userDTO.getUsername());
        newUser.setBirthdate(userDTO.getBirthdate());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setEmail(userDTO.getEmail());

        userRepository.save(newUser);

        return modelMapper.map(newUser, UserResponseDTO.class);
    }
}
