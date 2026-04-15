package com.smartparking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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

    @Column(nullable = false, unique = true, length = 50)
    String name; // Car, Motorbike, Bicycle

    @Column(length = 100)
    String dimensions;
}
