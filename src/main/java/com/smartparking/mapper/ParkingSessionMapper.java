package com.smartparking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.smartparking.dto.request.ParkingCheckInRequest;
import com.smartparking.dto.response.ParkingCheckInResponse;
import com.smartparking.dto.response.ParkingCheckOutResponse;
import com.smartparking.dto.response.ParkingSessionResponse;
import com.smartparking.entity.ParkingSession;
import com.smartparking.entity.Payment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingSessionMapper {
    ParkingSession toParkingSession(ParkingCheckInRequest request);

    ParkingCheckInResponse toParkingCheckInResponse(ParkingSession parkingSession);

    ParkingSessionResponse toParkingSessionResponse(ParkingSession parkingSession);

    @Mapping(target = "paymentId", source = "payment.paymentId")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "availableMethods", expression = "java(java.util.List.of(\"CASH\", \"VNPAY\", \"MOMO\"))")
    ParkingCheckOutResponse toParkingCheckOutResponse(ParkingSession parkingSession, Payment payment);
}
