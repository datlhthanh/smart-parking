package com.smartparking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailService {
    JavaMailSender javaMailSender;

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Mã xác nhận lấy lại mật khẩu - Smart Parking");
        message.setText("Mã xác nhận của bạn là: " + otp + ". Mã này sẽ hết hạn trong 5 phút. Vui lòng không chia sẻ cho bất kỳ ai.");

        javaMailSender.send(message);
        log.info("Đã gửi email chứa OTP tới: {}", to);
    }
}
