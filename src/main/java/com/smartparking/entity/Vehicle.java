package com.smartparking.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vehicle extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    String licensePlate;

    @Column(length = 50)
    String color;

    @Column(length = 50)
    String brand;

    @Column(length = 500)
    String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    VehicleType vehicleType;
}
