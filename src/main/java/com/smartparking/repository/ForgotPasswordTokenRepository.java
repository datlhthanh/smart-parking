package com.smartparking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartparking.entity.ForgotPasswordToken;
import com.smartparking.entity.User;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Long> {
    Optional<ForgotPasswordToken> findByUserAndOtp(User user, String otp);
}
