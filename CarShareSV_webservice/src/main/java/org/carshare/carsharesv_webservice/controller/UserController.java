package org.carshare.carsharesv_webservice.controller;

import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.service.iUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.carshare.carsharesv_webservice.util.Constants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API + USER_CONTROLLER)
public class UserController {

    private final iUserService userService;

    //ADMIN ENDPOINT
    @GetMapping(GET_ALL)
    public ResponseEntity<GenericResponse> getAllUsers() {
        return GenericResponse.builder()
                .data(userService.getAllUsers())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_ACTIVE)
    public ResponseEntity<GenericResponse> getAllActiveUsers() {
        return GenericResponse.builder()
                .data(userService.getAllActiveUsers())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    //ADMIN ENDPOINT
    @GetMapping(GET_ALL_NOT_ACTIVE)
    public ResponseEntity<GenericResponse> getAllNotActiveUsers() {
        return GenericResponse.builder()
                .data(userService.getAllNotActiveUsers())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<GenericResponse> getUserById(@RequestParam("id") UUID userId) {
        return GenericResponse.builder()
                .data(userService.getUserById(userId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_BY_USERNAME_OR_EMAIL + "/{identifier}")
    public ResponseEntity<GenericResponse> getUserByUsernameOrEmail(@PathVariable("identifier") String identifier) {
        return GenericResponse.builder()
                .data(userService.getUserByUsernameOrEmail(identifier))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }
}
