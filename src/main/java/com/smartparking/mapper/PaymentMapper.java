package com.smartparking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.smartparking.dto.response.PaymentResponse;
import com.smartparking.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "parkingSessionId", source = "parkingSession.parkingSessionId")
    PaymentResponse toPaymentResponse(Payment payment);
}
