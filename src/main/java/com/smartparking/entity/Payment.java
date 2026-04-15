package com.smartparking.entity;

import jakarta.persistence.*;

import com.smartparking.enums.PaymentMethod;
import com.smartparking.enums.PaymentStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {

    @Column(nullable = false)
    Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    PaymentStatus status;

    @Column(length = 100)
    String transactionNo; // mã giao dịch trả về từ VNPay/Momo

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    // thanh toán này có thể thuộc về Booking (đặt chỗ)
    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;

    // hoặc thanh toán này thuộc về mua vé tháng
    @ManyToOne
    @JoinColumn(name = "monthly_pass_id")
    MonthlyPass monthlyPass;
}
