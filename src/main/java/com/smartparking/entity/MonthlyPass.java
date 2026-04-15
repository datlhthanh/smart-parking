package com.smartparking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "monthly_passes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyPass extends BaseEntity {

    @Column(nullable = false)
    LocalDate startDate;

    @Column(nullable = false)
    LocalDate endDate;

    @Column(nullable = false)
    Double price;

    @Column(nullable = false)
    Boolean isActive; // true nếu vé còn hạn, false nếu hết hạn

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    Vehicle vehicle; // 1 xe chỉ có 1 vé tháng active tại 1 thời điểm
}
