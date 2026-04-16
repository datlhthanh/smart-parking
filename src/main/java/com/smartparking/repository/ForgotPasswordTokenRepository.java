package com.smartparking.repository;

import com.smartparking.entity.ForgotPasswordToken;
import com.smartparking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Long> {
    Optional<ForgotPasswordToken> findByUserAndOtp(User user, String otp);
}
