package org.carshare.carsharesv_webservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.domain.dto.JwtAuthResponse;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateUserDTO;
import org.carshare.carsharesv_webservice.domain.dto.request.LoginRequestDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.service.iAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.carshare.carsharesv_webservice.util.Constants.*;

@CrossOrigin
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

    @PostMapping(LOGIN)
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequestDTO body) {
        String token = authService.login(body);

        JwtAuthResponse res = JwtAuthResponse.builder().accessToken(token).build();

        return ResponseEntity.ok(res);
    }

    @GetMapping(WHOAMI)
    public ResponseEntity<GenericResponse> whoami() {
        return GenericResponse.builder()
                .data(authService.whoami())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }
}
