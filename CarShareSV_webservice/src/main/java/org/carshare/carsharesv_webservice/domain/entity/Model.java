package org.carshare.carsharesv_webservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modelId;

    @Column(unique = true)
    private String model;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
