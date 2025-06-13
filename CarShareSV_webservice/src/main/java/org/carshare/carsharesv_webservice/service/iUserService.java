package org.carshare.carsharesv_webservice.service;


import org.carshare.carsharesv_webservice.domain.dto.create.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface iUserService {
    UserResponseDTO register(CreateUserDTO userDTO) throws Exception;
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(UUID userId);
    UserResponseDTO getUserByUsernameOrEmail(String identifier);
}
