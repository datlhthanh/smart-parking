package com.smartparking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartparking.dto.request.PaymentConfirmRequest;
import com.smartparking.dto.response.PaymentResponse;
import com.smartparking.entity.ParkingSession;
import com.smartparking.entity.ParkingSlot;
import com.smartparking.entity.Payment;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.ParkingSessionStatus;
import com.smartparking.enums.PaymentStatus;
import com.smartparking.enums.SlotStatus;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.PaymentMapper;
import com.smartparking.repository.ParkingSessionRepository;
import com.smartparking.repository.ParkingSlotRepository;
import com.smartparking.repository.PaymentRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentService {
    PaymentRepository paymentRepository;
    ParkingSessionRepository parkingSessionRepository;
    ParkingSlotRepository parkingSlotRepository;
    PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponse confirmPayment(PaymentConfirmRequest request) {
        Payment payment = paymentRepository
                .findById(request.getPaymentId())
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new AppException(ErrorCode.PAYMENT_ALREADY_PROCESSED);
        }

        // cập nhật payment
        payment.setMethod(request.getMethod());
        payment.setTransactionNo(request.getTransactionNo());
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        // cập nhật session và slot trong cùng transaction
        ParkingSession parkingSession = payment.getParkingSession();
        parkingSession.setStatus(ParkingSessionStatus.COMPLETED);
        parkingSessionRepository.save(parkingSession);

        ParkingSlot parkingSlot = parkingSession.getParkingSlot();
        parkingSlot.setStatus(SlotStatus.AVAILABLE);
        parkingSlotRepository.save(parkingSlot);

        return paymentMapper.toPaymentResponse(payment);
    }
}
