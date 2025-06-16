package org.carshare.carsharesv_webservice.domain.dto.response;

import org.carshare.carsharesv_webservice.domain.entity.Brand;
import org.carshare.carsharesv_webservice.domain.entity.Model;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.domain.entity.Year;

import java.util.UUID;

public class CarResponseDTO {

    private UUID carId;

    private String plateNumber;

    private Integer doors;

    private Integer capacity;

    private Float dailyPrice;

    private Integer year;

    private String userName;

    private String model;

    private String brand;

}
