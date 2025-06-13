package org.carshare.carsharesv_webservice.domain.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    @NotEmpty
    @NotNull
    @NotBlank
    private String firstName;

    @NotEmpty
    @NotNull
    @NotBlank
    private String lastName;

    @NotEmpty
    @NotNull
    @NotBlank
    private String username;

    /*@NotEmpty
    @NotNull
    @NotBlank
    private String password; */ // -> to be implemented

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate birthdate;

    @NotEmpty
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\d{8}$\n", message = "Phone number must be valid")
    private String phoneNumber;

    @NotEmpty
    @NotNull
    @NotBlank
    @Email(message = "Email must be valid")
    private String email;
}
