package org.carshare.carsharesv_webservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String firstName;

    private String lastName;

    private String username;

    private LocalDate birthdate;

    private String phoneNumber;

    private String email;
}
