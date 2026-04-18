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
    String id;

    String otp; // chứa mã 6 số
    LocalDateTime expiryTime; // thời gian hết hạn

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
