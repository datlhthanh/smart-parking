package com.smartparking.entity;

import jakarta.persistence.*;

import com.smartparking.enums.SlotStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "parking_slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingSlot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long parkingSlotId;

    @Column(nullable = false, length = 50)
    String name; // A1, A2...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    SlotStatus status; // AVAILABLE, OCCUPIED...

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    ParkingZone parkingZone;

    @Builder.Default
    Boolean isEnabled = true;
}
