package org.carshare.carsharesv_webservice.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.carshare.carsharesv_webservice.domain.entity.Brand;
import org.carshare.carsharesv_webservice.domain.entity.Model;
import org.carshare.carsharesv_webservice.domain.entity.Year;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarDTO {

  @NotNull
  @Min(2)
  @Max(5)
  private Integer doors;

  @NotNull
  @Min(1)
  @Max(7)
  private Integer capacity;
  
  @NotNull
  @Positive
  private Float dailyPrice;

  @NotEmpty
  @NotNull
  @NotBlank
  @Pattern(regexp = "^P[0-9A-F]{3,6}$")
  private String plateNumber;

  @NotEmpty
  @NotNull
  @NotBlank
  @Length(min = 3, max = 100, message = "Description must be min 3 and max 100 characters long")
  private String description;

  @NotNull
  @Min(2015)
  @Max(2025)
  private Integer year;

  @NotEmpty
  @NotNull
  @NotBlank
  private String model;

  @NotEmpty
  @NotNull
  @NotBlank
  private String brand;
}
