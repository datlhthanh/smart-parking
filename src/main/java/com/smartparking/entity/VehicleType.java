package com.smartparking.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vehicle_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long vehicleTypeId;

    @Column(nullable = false, unique = true, length = 50)
    String name; // Car, Motorbike, Bicycle

    @Column(nullable = false)
    BigDecimal basePrice;

    @Column(length = 255)
    String description;
}
