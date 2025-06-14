package org.carshare.carsharesv_webservice.service;

import org.carshare.carsharesv_webservice.domain.dto.create.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;

public interface iAuthService {
    UserResponseDTO register(CreateUserDTO userDTO) throws Exception;
}
