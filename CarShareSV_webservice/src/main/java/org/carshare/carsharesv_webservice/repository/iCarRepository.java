package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iCarRepository extends JpaRepository<Car, UUID> {

    List<Car> findCarsByUserId(UUID userId);
    List<Car> findCarsByYear(Integer yearId);
    List<Car> findAll();
    Optional<Car> findCarById(UUID carId);
    Optional<Car> findCarByPlateNumber(String plateNumber);
    List<Car> findCarsByBrand(Integer brandId);
    List<Car> findCarsByModel(Integer modelId);

}
