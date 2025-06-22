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

import static org.carshare.carsharesv_webservice.util.Constants.*;

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
}
