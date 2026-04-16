package com.smartparking.service.validator;

import org.springframework.stereotype.Component;

import com.smartparking.enums.ErrorCode;
import com.smartparking.exception.AppException;
import com.smartparking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void checkEmailAndPhoneExists(String email, String phoneNumber) {
        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
    }

    public void checkPhoneExists(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
    }
}
