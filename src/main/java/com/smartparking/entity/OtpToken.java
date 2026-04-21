package com.smartparking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "otp_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    String id;

    @Column(nullable = false, length = 10)
    String otp; // chứa mã 6 số

    @Column(nullable = false)
    LocalDateTime expiryTime; // thời gian hết hạn

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
