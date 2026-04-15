package com.smartparking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.smartparking.enums.BookingStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking extends BaseEntity {

    @Column(nullable = false)
    LocalDateTime bookingTime;

    @Column(nullable = false)
    LocalDateTime expectedArrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    BookingStatus status; // Enum: PENDING, CONFIRMED, CANCELLED, COMPLETED

    @Column(length = 255)
    String note;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "parking_slot_id", nullable = false)
    ParkingSlot parkingSlot;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    Vehicle vehicle;
}
