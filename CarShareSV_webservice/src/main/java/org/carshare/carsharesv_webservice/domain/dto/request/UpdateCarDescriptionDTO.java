package org.carshare.carsharesv_webservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarDescriptionDTO {
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 100, message = "Description must be min 3 and max 100 characters long")
    private String description;
}
