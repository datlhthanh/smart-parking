package com.smartparking.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

import com.smartparking.enums.PaymentMethod;
import com.smartparking.enums.PaymentStatus;
import com.smartparking.enums.PaymentType;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentId;

    @Column(nullable = false, length = 20)
    PaymentType type; // PARKING_SESSION, BOOKING, MONTHLY_PASS

    @Column(nullable = false)
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    PaymentMethod method; // CASH, VNPAY, MOMO

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    PaymentStatus status; // PENDING, SUCCESS, FAILED

    @Column(length = 50)
    String transactionNo; // mã giao dịch trả về từ VNPay/Momo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_session_id")
    ParkingSession parkingSession;

    // thanh toán này có thể thuộc về Booking (đặt chỗ)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    Booking booking;

    // hoặc thanh toán này thuộc về mua vé tháng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_pass_id")
    MonthlyPass monthlyPass;
}
