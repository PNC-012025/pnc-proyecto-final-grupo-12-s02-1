package org.carshare.carsharesv_webservice.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {

    @NotBlank
    @NotEmpty
    @Length(min =10, max = 255, message = "Description must be min 10 and max 255 characters long")
    private String comment;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer rating;

}
