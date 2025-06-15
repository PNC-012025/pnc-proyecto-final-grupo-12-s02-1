package org.carshare.carsharesv_webservice.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 3, max = 12, message = "Username must be min 3 and max 12 characters long")
    private String username;

    @NotEmpty
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a number, and a special character.")
    private String password;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate birthdate;

    @NotEmpty
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\d{8}$", message = "Phone number must be valid")
    private String phoneNumber;

    @NotEmpty
    @NotNull
    @NotBlank
    @Email(message = "Email must be valid")
    private String email;
}
