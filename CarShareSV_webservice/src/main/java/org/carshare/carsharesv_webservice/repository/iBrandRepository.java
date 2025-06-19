package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface iBrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findBrandByBrand(String brand);
}
