package org.carshare.carsharesv_webservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.carshare.carsharesv_webservice.domain.entity.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID userId;

    private String firstName;

    private String lastName;

    private String username;

    private LocalDate birthdate;

    private String phoneNumber;

    private String email;

    private Boolean active;

    private List<String> roles;
}
