package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface iModelRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findModelByModel(String model);
}
