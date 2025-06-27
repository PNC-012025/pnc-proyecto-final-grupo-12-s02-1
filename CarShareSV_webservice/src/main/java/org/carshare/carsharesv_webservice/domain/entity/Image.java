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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageId;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false, foreignKey = @ForeignKey(name = "fk_car_id"))
    private Car car;
}
