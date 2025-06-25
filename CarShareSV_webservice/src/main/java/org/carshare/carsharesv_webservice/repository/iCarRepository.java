package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iCarRepository extends JpaRepository<Car, UUID> {

    List<Car> findCarsByUserUserId(UUID userId);
    List<Car> findAllCarsByVisible(Boolean visible);
    Optional<Car> findCarByCarId(UUID carId);
    Optional<Car> findCarByPlateNumber(String plateNumber);
    List<Car> findCarsByBrandBrandIdAndVisible(Integer brandId, Boolean visible);
    List<Car> findCarsByModelModelIdAndVisible(Integer modelId, Boolean visible);
    List<Car> findCarsByYearYearIdAndVisible(Integer yearId, Boolean visible);
    List<Car> findCarsByTransmissionTransmissionIdAndVisible(Integer transmissionTransmissionId, Boolean visible);
}
