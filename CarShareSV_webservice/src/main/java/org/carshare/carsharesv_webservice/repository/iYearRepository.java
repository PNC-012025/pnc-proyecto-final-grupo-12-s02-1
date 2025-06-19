package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface iYearRepository extends JpaRepository<Year, Integer> {
    Optional<Year> findYearByYear(Integer year);
}
