package org.carshare.carsharesv_webservice.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateCarDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.CarResponseDTO;

import java.util.List;
import java.util.UUID;

public interface iCarService {

    CarResponseDTO saveCar(CreateCarDTO car);
    List<CarResponseDTO> getAllCars();
    List<CarResponseDTO> getAllCarsByModel(Integer modelId);
    List<CarResponseDTO> getAllCarsByBrand(Integer brand);
    List<CarResponseDTO> getAllCarsByYear(Integer year);
    List<CarResponseDTO> getAllCarsByUserId(UUID userId);
    CarResponseDTO getCarById(UUID carId);
    CarResponseDTO updateCarByDailyPrice(UUID carId, @NotEmpty @NotNull @NotBlank @Positive float price);
    void deleteCarById(UUID carId);
}
