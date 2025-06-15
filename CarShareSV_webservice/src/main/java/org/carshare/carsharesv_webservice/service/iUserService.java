package org.carshare.carsharesv_webservice.service;


import jakarta.validation.constraints.*;
import org.carshare.carsharesv_webservice.domain.dto.response.RoleResponseDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.UserResponseDTO;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

public interface iUserService {
    List<UserResponseDTO> getAllUsers();
    List<UserResponseDTO> getAllActiveUsers();
    List<UserResponseDTO> getAllNotActiveUsers();
    UserResponseDTO getUserById(UUID userId);
    UserResponseDTO getUserByUsernameOrEmail(String identifier);
    List<RoleResponseDTO> getAllUserRoles(UUID userId);
    void deactivateUser(UUID userId);
    void activateUser(UUID userId);
    UserResponseDTO updateUserFirstName(UUID userId,@NotEmpty @NotNull @NotBlank String firstName);
    UserResponseDTO updateUserLastName(UUID userId, @NotEmpty @NotNull @NotBlank String lastName);
    UserResponseDTO updateUserUsername(UUID userId, @NotEmpty @NotNull @NotBlank @Length(min = 3, max = 12, message = "Username must be min 3 and max 12 characters long") String username);
    UserResponseDTO updateUserEmail(UUID userId, @NotEmpty @NotNull @NotBlank @Email(message = "Email must be valid") String email);
    UserResponseDTO updateUserPhoneNumber(UUID userId, @NotEmpty @NotNull @NotBlank @Pattern(regexp = "^\\d{8}$", message = "Phone number must be valid") String phoneNumber);
    void grantAdminRoleToUser(UUID userId);
    void revokeAdminRoleFromUser(UUID userId);
}
