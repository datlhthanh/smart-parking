package com.smartparking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.smartparking.enums.ParkingSessionStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "parking_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingSession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long parkingSessionId;

    @Column(nullable = false)
    LocalDateTime checkInTime;

    LocalDateTime checkOutTime;

    BigDecimal totalFee;

    @Column(nullable = false, length = 20)
    String licensePlate;

    @Column(length = 255)
    String checkInImage;

    @Column(length = 255)
    String checkOutImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ParkingSessionStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle; // có thể null nếu xe vãng lai

    @ManyToOne
    @JoinColumn(name = "parking_slot_id")
    ParkingSlot parkingSlot;
}
