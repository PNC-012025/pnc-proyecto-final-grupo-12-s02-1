package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iCarRepository extends JpaRepository<Car, UUID> {

    List<Car> findCarsByUserUserId(UUID userId);
    List<Car> findAll();
    Optional<Car> findCarByCarId(UUID carId);
    Optional<Car> findCarByPlateNumber(String plateNumber);
    List<Car> findCarsByBrandBrandId(Integer brandId);
    List<Car> findCarsByModelModelId(Integer modelId);

    @Modifying
    @Query(value = "SELECT * FROM car WHERE year_id =: yearId",
            nativeQuery = true)
    List<Car> findCarsByYearId(Integer yearId);

}
