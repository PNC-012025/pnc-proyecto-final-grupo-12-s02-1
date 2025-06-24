package org.carshare.carsharesv_webservice.service;

import org.carshare.carsharesv_webservice.domain.dto.request.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.request.LoginRequestDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.WhoamiResponseDTO;

public interface iAuthService {
    UserResponseDTO register(CreateUserDTO userDTO) throws Exception;
    String login(LoginRequestDTO loginRequestDTO);
    WhoamiResponseDTO whoami();
}
