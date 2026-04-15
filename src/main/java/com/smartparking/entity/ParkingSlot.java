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

    @Column(nullable = false, length = 50)
    String name; // A1, A2, B1

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    SlotStatus status; // Enum: AVAILABLE, OCCUPIED, MAINTENANCE

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    ParkingZone parkingZone;
}
