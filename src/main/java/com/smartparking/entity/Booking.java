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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    BookingStatus status; // PENDING, CONFIRMED, CANCELLED, COMPLETED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_slot_id", nullable = false)
    ParkingSlot parkingSlot;

    @Column(nullable = false)
    LocalDateTime bookingTime;

    @Column(nullable = false)
    LocalDateTime expectedArrivalTime;

    @Column(length = 500)
    String note;
}
