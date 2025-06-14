package org.carshare.carsharesv_webservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.domain.dto.create.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.service.iAuthService;
import org.carshare.carsharesv_webservice.service.iUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.carshare.carsharesv_webservice.util.Constants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API + AUTH_CONTROLLER)
public class AuthController {
    private final iAuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<GenericResponse> register(@RequestBody @Valid CreateUserDTO body) throws Exception {
        UserResponseDTO data = authService.register(body);

        return GenericResponse.builder()
                .message("User succesfully registered")
                .data(data)
                .status(HttpStatus.CREATED)
                .build().buildResponse();
    }
}
