package org.carshare.carsharesv_webservice.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.carshare.carsharesv_webservice.domain.entity.Brand;
import org.carshare.carsharesv_webservice.domain.entity.Model;
import org.carshare.carsharesv_webservice.domain.entity.Year;

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
  private Year year;

  @NotEmpty
  @NotNull
  @NotBlank
  private Model model;

  @NotEmpty
  @NotNull
  @NotBlank
  private Brand brand;

}
