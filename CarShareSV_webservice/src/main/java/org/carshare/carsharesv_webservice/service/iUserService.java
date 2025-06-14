package org.carshare.carsharesv_webservice.service;


import org.carshare.carsharesv_webservice.domain.dto.response.RoleResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface iUserService {
    List<UserResponseDTO> getAllUsers();
    List<UserResponseDTO> getAllActiveUsers();
    List<UserResponseDTO> getAllNotActiveUsers();
    UserResponseDTO getUserById(UUID userId);
    UserResponseDTO getUserByUsernameOrEmail(String identifier);
    List<RoleResponseDTO> getAllUserRoles(UUID userId);
    //void deactivateUser(UUID userId);
    //void activateUser(UUID userId);
}
