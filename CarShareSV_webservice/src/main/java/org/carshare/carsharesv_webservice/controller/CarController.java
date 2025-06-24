package org.carshare.carsharesv_webservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateCarDTO;
import org.carshare.carsharesv_webservice.domain.dto.request.UpdateCarDescriptionDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.CarResponseDTO;
import org.carshare.carsharesv_webservice.service.iCarService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.carshare.carsharesv_webservice.util.Constants.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(API + CARS_CONTROLLER)
public class CarController {

    private final iCarService carService;

    //Admin endpoint
    @GetMapping(GET_ALL)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN')")
    public ResponseEntity<GenericResponse> getAll() {
        return GenericResponse.builder()
                .data(carService.getAllCars())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_VISIBLE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllVisible() {
        return GenericResponse.builder()
                .data(carService.getAllVisibleCars())
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PostMapping(CREATE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> saveCar(@RequestBody @Valid CreateCarDTO body) {
        CarResponseDTO data = carService.saveCar(body);
        return GenericResponse.builder()
                .message("Car saved Successfully")
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> deleteCar(@RequestParam("id") UUID carId) {
        carService.deleteCarById(carId);
        return GenericResponse.builder()
                .message("Car deleted Successfully")
                .data(null)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_CARS_BY_MODEL + "/{modelId}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllCarsByModel(@PathVariable("modelId") Integer modelId) {
        return GenericResponse.builder()
                .data(carService.getAllCarsByModel(modelId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_CARS_BY_BRAND + "/{brandId}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllCarsByBrand(@PathVariable("brandId") Integer brandId) {
        return GenericResponse.builder()
                .data(carService.getAllCarsByBrand(brandId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_CARS_BY_YEAR + "/{year}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllCarsByYear(@PathVariable("year") Integer year) {
        return GenericResponse.builder()
                .data(carService.getAllCarsByYear(year))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_CAR_BY_ID + "/{carId}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getCarById(@PathVariable UUID carId) {
        return GenericResponse.builder()
                .data(carService.getCarById(carId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_DAILY_PRICE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateCarByDailyPrice(@RequestParam("id") UUID carId, @RequestParam("daily_price") @NotNull @Positive float price) {
        return GenericResponse.builder()
                .message("Daily price updated successfully")
                .data(carService.updateCarByDailyPrice(carId, price))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_DESCRIPTION)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateCarByDescription(@RequestParam("id") UUID carId, @RequestBody @Valid UpdateCarDescriptionDTO body  ) {

        return GenericResponse.builder()
                .message("Description updated successfully")
                .data(carService.updateCarByDescription(carId, body))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_CAR_BY_USER_ID + "/{userId}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getCarByUserId(@PathVariable("userId") UUID userId) {
        return GenericResponse.builder()
                .data(carService.getAllCarsByUserId(userId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }
}
