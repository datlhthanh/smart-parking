package com.smartparking.controller;

import java.text.ParseException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.smartparking.dto.request.*;
import com.smartparking.dto.response.*;
import com.smartparking.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthenticationService authenticationService;

    @PostMapping("/register")
    ApiResponse<UserRegisterResponse> register(@RequestBody UserRegisterRequest request) {
        var result = authenticationService.register(request);

        return ApiResponse.<UserRegisterResponse>builder()
                .message("User registered")
                .result(result)
                .build();
    }

    @PostMapping("/login")
    ApiResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        var result = authenticationService.login(request);

        return ApiResponse.<UserLoginResponse>builder()
                .message("Logged in")
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<AuthIntrospectResponse> introspect(@RequestBody AuthIntrospectRequest request) {
        var result = authenticationService.introspect(request);

        return ApiResponse.<AuthIntrospectResponse>builder()
                .message("Token introspected")
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody UserLogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);

        return ApiResponse.<Void>builder().message("Logged out").build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@RequestBody @Valid UserForgotPasswordRequest request) {
        authenticationService.forgotPassword(request);

        return ApiResponse.<Void>builder().message("Verification code sent").build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@RequestBody @Valid UserResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ApiResponse.<Void>builder().message("Password updated").build();
    }
}
