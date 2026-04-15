package com.smartparking.service;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartparking.dto.request.LoginRequest;
import com.smartparking.dto.request.RegisterRequest;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.entity.Role;
import com.smartparking.entity.User;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.RoleName;
import com.smartparking.enums.UserStatus;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.UserMapper;
import com.smartparking.repository.RoleRepository;
import com.smartparking.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    UserMapper userMapper;

    public UserResponse register(RegisterRequest request) {
        // kiểm tra tổn tại: email, phone number
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        // tim role USER trong danh sách role
        Role userRole = roleRepository
                .findByName(RoleName.USER.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // dùng mapper để tạo user mới, set các trường từ request
        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // set status mặc định là ACTIVE, set role là USER
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(Set.of(userRole));

        // lưu xuống DB
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse login(LoginRequest request) {
        // tìm user theo email
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        // kiểm tra password có khớp không
        if (!user.getPassword().equals(request.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        // trả về thông tin user
        return userMapper.toUserResponse(user);
    }
}
