package com.smartparking.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "parking_lots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingLot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long parkingLotId;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 255)
    String address;

    Double latitude;

    Double longitude;

    @Builder.Default
    @Column(nullable = false)
    Boolean isActive = true;
}
