package org.carshare.carsharesv_webservice.domain.dto.response;

import lombok.Data;
import org.carshare.carsharesv_webservice.domain.entity.Car;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReservationResponseDTO {
    private UUID reservationId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String address;

    private Float total;

    private String status; //ACTIVE, FINISHED, CANCELED

    private String reservingUsername;

    private CarResponseDTO reservedCar;
}
