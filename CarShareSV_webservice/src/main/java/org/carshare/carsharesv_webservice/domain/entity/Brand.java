package org.carshare.carsharesv_webservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;

    @Column(unique = true)
    private String brand;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Model> models;
}
