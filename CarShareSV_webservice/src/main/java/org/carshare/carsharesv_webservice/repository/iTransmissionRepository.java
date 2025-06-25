package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface iTransmissionRepository extends JpaRepository<Transmission, Integer> {
    Optional<Transmission> findByTransmission(String transmission);
}
