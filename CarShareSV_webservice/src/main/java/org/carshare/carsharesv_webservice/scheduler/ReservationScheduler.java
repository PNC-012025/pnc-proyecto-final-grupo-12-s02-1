package org.carshare.carsharesv_webservice.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.entity.Reservation;
import org.carshare.carsharesv_webservice.repository.iReservationRepository;
import org.carshare.carsharesv_webservice.util.Constants;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private final iReservationRepository reservationRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateExpiredReservations() {
        LocalDate currentDate = LocalDate.now();

        //get all active reservations that have already expired
        List<Reservation> expiredReservations = reservationRepository.findExpiredActiveReservations(currentDate);

        if (!expiredReservations.isEmpty()) {
            List<UUID> expiredReservationIds = expiredReservations.stream()
                    .map(Reservation::getReservationId)
                    .toList();

            // Update the status of the expired reservations to FINISHED
            reservationRepository.updateReservationsStatus(expiredReservationIds, Constants.FINISHED);
        }
    }
}
