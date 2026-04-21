package com.smartparking.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

import com.smartparking.enums.MonthlyPassStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "monthly_passes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyPass extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long monthlyPassId;

    @Column(nullable = false, length = 20)
    MonthlyPassStatus status; // PENDING_PAYMENT ACTIVE, EXPIRED, CANCELLED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    Vehicle vehicle; // 1 xe chỉ có 1 vé tháng active tại 1 thời điểm

    @Column(nullable = false)
    LocalDate startDate;

    @Column(nullable = false)
    LocalDate endDate;

    @Column(nullable = false)
    BigDecimal price;
}
