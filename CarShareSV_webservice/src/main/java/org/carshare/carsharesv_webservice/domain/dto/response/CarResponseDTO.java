package org.carshare.carsharesv_webservice.domain.dto.response;

import lombok.Data;
import org.carshare.carsharesv_webservice.domain.entity.Brand;
import org.carshare.carsharesv_webservice.domain.entity.Model;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.domain.entity.Year;

import java.util.UUID;

@Data
public class CarResponseDTO {

    private UUID carId;

    private String plateNumber;

    private String description;

    private Boolean visible;

    private Integer doors;

    private Integer capacity;

    private Float dailyPrice;

    private Integer year;

    private String username;

    private String model;

    private String brand;

}
