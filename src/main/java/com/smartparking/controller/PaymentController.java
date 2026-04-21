package com.smartparking.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartparking.dto.request.PaymentConfirmRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.PaymentResponse;
import com.smartparking.service.PaymentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @PostMapping("/confirm")
    ApiResponse<PaymentResponse> confirmPayment(@RequestBody @Valid PaymentConfirmRequest request) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.confirmPayment(request))
                .build();
    }
}
