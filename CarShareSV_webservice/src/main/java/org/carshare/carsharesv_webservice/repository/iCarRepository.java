package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iCarRepository extends JpaRepository<Car, UUID> {

    @Query(value = "SELECT * FROM car WHERE car.user_id = :userId ORDER BY created_at DESC;",
            nativeQuery = true)
    List<Car> findCarsByUserUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT * FROM car WHERE car.visible = :visible ORDER BY created_at DESC;",
            nativeQuery = true)
    List<Car> findAllCarsByVisible(@Param("visible") Boolean visible);
    Optional<Car> findCarByCarId(UUID carId);
    Optional<Car> findCarByPlateNumber(String plateNumber);
    List<Car> findCarsByBrandBrandIdAndVisible(Integer brandId, Boolean visible);
    List<Car> findCarsByModelModelIdAndVisible(Integer modelId, Boolean visible);
    List<Car> findCarsByYearYearIdAndVisible(Integer yearId, Boolean visible);
    List<Car> findCarsByTransmissionTransmissionIdAndVisible(Integer transmissionTransmissionId, Boolean visible);
}
