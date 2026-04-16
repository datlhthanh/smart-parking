package com.smartparking.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "parking_zones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingZone extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long parkingZoneId;

    @Column(nullable = false, unique = true, length = 50)
    String name; // Zone A, Zone B

    @Column(nullable = false)
    Integer capacity;

    @ManyToOne
    @JoinColumn(name = "allowed_vehicle_type_id")
    VehicleType allowedVehicleType;
}
