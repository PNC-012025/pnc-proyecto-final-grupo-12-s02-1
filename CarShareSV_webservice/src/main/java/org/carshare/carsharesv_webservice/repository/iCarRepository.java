package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Brand;
import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iCarRepository extends JpaRepository<Car, UUID> {

    List<Car> findCarsByUserId(UUID userId);
    List<Car> findCarsByYear(Integer year);
    Optional<Car> findCarById(UUID carId);
    List<Car> findCarsByBrand(String brand);
    List<Car> findCarsByModel(String model);
}
