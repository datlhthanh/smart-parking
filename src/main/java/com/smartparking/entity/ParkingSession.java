package com.smartparking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

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

    @Column(nullable = false)
    LocalDateTime checkInTime;

    LocalDateTime checkOutTime; // có thể null vì lúc mới vào chưa ra

    Double totalFee; // phí gửi xe (nếu là vé ngày)

    @Column(length = 255)
    String checkInImage; // ảnh chụp biển số lúc vào

    @Column(length = 255)
    String checkOutImage; // ảnh chụp biển số lúc ra

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "parking_slot_id")
    ParkingSlot parkingSlot;
}
