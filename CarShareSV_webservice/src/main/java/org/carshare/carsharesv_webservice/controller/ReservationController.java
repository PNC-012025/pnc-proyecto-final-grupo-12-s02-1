package org.carshare.carsharesv_webservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateReservationDTO;
import org.carshare.carsharesv_webservice.service.iReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.carshare.carsharesv_webservice.util.Constants.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(API + RESERVATION_CONTROLLER)
public class ReservationController {
    private final iReservationService reservationService;

    @PostMapping(CREATE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> createReservation(@RequestBody @Valid CreateReservationDTO body) {
        return GenericResponse.builder()
                .data(reservationService.createReservation(body))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllCarReservations(@RequestParam("id") UUID carId) {
        return GenericResponse.builder()
                .data(reservationService.getAllCarReservations(carId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_RESERVATIONS_BY_USER)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllUserReservations(@RequestParam("id") UUID userId) {
        return GenericResponse.builder()
                .data(reservationService.getAllUserReservations(userId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(CANCEL_RESERVATION + "/{id}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> cancelReservation(@PathVariable("id") UUID reservationId) {
        reservationService.cancelReservation(reservationId);

        return GenericResponse.builder()
                .message("Reservation succesfully cancelled")
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_CAR_RESERVED_DATES)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllCarReservedDates(@RequestParam("id") UUID carId) {
        return GenericResponse.builder()
                .data(reservationService.getAllCarReservedDates(carId))
                .status(HttpStatus.OK)
                .build().buildResponse();
    }
}
