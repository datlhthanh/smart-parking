package com.smartparking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.smartparking.enums.VehicleRegistrationStatus;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long vehicleId;

    @Column(nullable = false, unique = true, length = 20)
    String licensePlate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    VehicleType vehicleType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    VehicleRegistrationStatus status = VehicleRegistrationStatus.PENDING;

    @Column(length = 500)
    String rejectReason;

    @Column(name = "approved_at")
    LocalDateTime approvedAt;
}
