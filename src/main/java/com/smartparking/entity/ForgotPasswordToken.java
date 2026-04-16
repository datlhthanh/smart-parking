package com.smartparking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "forgot_pw_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String otp; // chứa mã 6 số
    LocalDateTime expiryTime; // thời gian hết hạn

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
