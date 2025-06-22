package org.carshare.carsharesv_webservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reservationId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String address;

    @Column
    private Float total;

    @Column
    private String status; //ACTIVE, FINISHED, CANCELED

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reservation_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reservation_car"))
    private Car car;
}

