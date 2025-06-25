package org.carshare.carsharesv_webservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID carId;

    @Column
    private Integer doors;

    @Column
    private Integer capacity;

    @Column
    private Float dailyPrice;

    @Column
    private String plateNumber;

    @Column
    private String description;

    @Column
    private Boolean visible;

    @Column
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_car_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "year_id", nullable = false, foreignKey = @ForeignKey(name = "fk_car_year"))
    private Year year;

    @ManyToOne
    @JoinColumn(name = "mode_id", nullable = false, foreignKey = @ForeignKey(name = "fk_car_model"))
    private Model model;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false, foreignKey = @ForeignKey(name = "fk_car_brand"))
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "transmission_id", nullable = false, foreignKey = @ForeignKey(name = "fk_car_transmission"))
    private Transmission transmission;
}
