package org.carshare.carsharesv_webservice.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.carshare.carsharesv_webservice.service.iUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.carshare.carsharesv_webservice.util.Constants.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(API + USER_CONTROLLER)
public class UserController {

    private final iUserService userService;

    //ADMIN ENDPOINT
    @GetMapping(GET_ALL)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN')")
    public ResponseEntity<GenericResponse> getAllUsers() {
        return GenericResponse.builder()
                .data(userService.getAllUsers())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_ACTIVE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllActiveUsers() {
        return GenericResponse.builder()
                .data(userService.getAllActiveUsers())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    //ADMIN ENDPOINT
    @GetMapping(GET_ALL_NOT_ACTIVE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN')")
    public ResponseEntity<GenericResponse> getAllNotActiveUsers() {
        return GenericResponse.builder()
                .data(userService.getAllNotActiveUsers())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getUserById(@RequestParam("id") UUID userId) {
        return GenericResponse.builder()
                .data(userService.getUserById(userId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_BY_USERNAME_OR_EMAIL + "/{identifier}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getUserByUsernameOrEmail(@PathVariable("identifier") String identifier) {
        return GenericResponse.builder()
                .data(userService.getUserByUsernameOrEmail(identifier))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_USER_ROLES)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN')")
    public ResponseEntity<GenericResponse> getUserRoles(@RequestParam("id") UUID userId) {
        return GenericResponse.builder()
                .data(userService.getAllUserRoles(userId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(DEACTIVATE + "/{id}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> deactivateUserById(@PathVariable("id")  UUID userId) {
        userService.deactivateUser(userId);

        return GenericResponse.builder()
                .message("User succesfully deactivated")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(ACTIVATE + "/{id}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> activateUserById(@PathVariable("id")  UUID userId) {
        userService.activateUser(userId);

        return GenericResponse.builder()
                .message("User succesfully activated")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_FIRSTNAME)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateUserFirstName(@RequestParam("id") UUID userId, @RequestParam("firstName") String firstName) {
        return GenericResponse.builder()
                .message("User firstName succesfully updated")
                .data(userService.updateUserFirstName(userId, firstName))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_LASTNAME)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateUserLastName(@RequestParam("id") UUID userId, @RequestParam("lastName") String lastName) {
        return GenericResponse.builder()
                .message("User lastName succesfully updated")
                .data(userService.updateUserLastName(userId, lastName))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_USERNAME)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateUserUsername(@RequestParam("id") UUID userId, @RequestParam("username") String username) {
        return GenericResponse.builder()
                .message("Username succesfully updated")
                .data(userService.updateUserUsername(userId, username))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_EMAIL)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateUserEmail(@RequestParam("id") UUID userId, @RequestParam("email") String email) {
        return GenericResponse.builder()
                .message("User email succesfully updated")
                .data(userService.updateUserEmail(userId, email))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_PHONENUMBER)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateUserPhoneNumber(@RequestParam("id") UUID userId, @RequestParam("phoneNumber") String phoneNumber) {
        return GenericResponse.builder()
                .message("User phoneNumber succesfully updated")
                .data(userService.updateUserPhoneNumber(userId, phoneNumber))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(GRANT_ADMIN_ROLE + "/{id}")
    @PreAuthorize("hasRole('SYSADMIN')")
    @Transactional
    public ResponseEntity<GenericResponse> grantAdminRoleToUser(@PathVariable("id") UUID userId) {
        userService.grantAdminRoleToUser(userId);

        return GenericResponse.builder()
                .message("Admin Role Granted to user succesfully")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(REVOKE_ADMIN_ROLE + "/{id}")
    @PreAuthorize("hasRole('SYSADMIN')")
    @Transactional
    public ResponseEntity<GenericResponse> revokeAdminRoleFromUser(@PathVariable("id") UUID userId) {
        userService.revokeAdminRoleFromUser(userId);

        return GenericResponse.builder()
                .message("Admin Role revoked to user succesfully")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }
}
