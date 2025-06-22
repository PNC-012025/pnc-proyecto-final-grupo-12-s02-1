package org.carshare.carsharesv_webservice.domain.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationResponseDTO {
    private LocalDate startDate;

    private LocalDate endDate;

    private String address;

    private Float total;

    private String status; //ACTIVE, FINISHED, CANCELED

    private String reservingUsername;

    private String carPlateNumber;
}
