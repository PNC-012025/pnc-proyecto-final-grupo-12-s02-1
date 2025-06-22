package org.carshare.carsharesv_webservice.service;

import org.carshare.carsharesv_webservice.domain.dto.request.CreateReservationDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReservationResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface iReservationService {
    ReservationResponseDTO createReservation(CreateReservationDTO reservationDTO);
    List<ReservationResponseDTO> getAllCarReservations(UUID carId);
    List<ReservationResponseDTO> getAllUserReservations(UUID userId);
    void cancelReservation(UUID reservationId);
    List<LocalDate> getAllCarReservedDates(UUID carId);
}
