package com.smartparking.controller;

import org.springframework.web.bind.annotation.*;

import com.smartparking.dto.request.LoginRequest;
import com.smartparking.dto.request.RegisterRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.register(request))
                .build();
    }

    @PostMapping("/login")
    ApiResponse<UserResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.login(request))
                .build();
    }
}
