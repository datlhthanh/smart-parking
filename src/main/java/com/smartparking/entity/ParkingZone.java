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

    @Column(nullable = false, length = 50)
    String name; // A, B, B1, Floor 1...

    @Column(nullable = false)
    Integer capacity;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id", nullable = false)
    ParkingLot parkingLot;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    VehicleType allowedVehicleType;
}
