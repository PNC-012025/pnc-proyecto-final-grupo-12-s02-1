package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface iReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query(value = "SELECT COUNT(r) > 0 FROM reservation r " +
            "WHERE r.car_id = :carId " +
            "AND r.status = 'ACTIVE'" +
            "AND ((r.start_date BETWEEN :startDate AND :endDate) " +
            "OR (r.end_date BETWEEN :startDate AND :endDate) " +
            "OR (:startDate BETWEEN r.start_date AND r.end_date) " +
            "OR (:endDate BETWEEN r.start_date AND r.end_date))", nativeQuery = true)
    boolean existsOverlappingReservation(
            @Param("carId") UUID carId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT * FROM reservation r WHERE r.car_id = :carId", nativeQuery = true)
    List<Reservation> findAllByCarId(@Param("carId") UUID carId);

    @Query(value = "SELECT * FROM reservation r WHERE r.user_id = :userId", nativeQuery = true)
    List<Reservation> findAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT r FROM Reservation r WHERE r.status = 'ACTIVE' AND r.endDate < :currentDate")
    List<Reservation> findExpiredActiveReservations(@Param("currentDate") LocalDate currentDate);

    @Modifying
    @Query("UPDATE Reservation r SET r.status = :status WHERE r.reservationId IN :reservationIds")
    void updateReservationsStatus(@Param("reservationIds") List<UUID> reservationIds, @Param("status") String status);

}
