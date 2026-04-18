package com.smartparking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.smartparking.dto.request.ParkingCheckInRequest;
import com.smartparking.dto.response.ParkingCheckInResponse;
import com.smartparking.dto.response.ParkingCheckOutResponse;
import com.smartparking.dto.response.ParkingSessionResponse;
import com.smartparking.entity.ParkingSession;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingSessionMapper {
    ParkingSession toParkingSession(ParkingCheckInRequest request);

    ParkingCheckInResponse toParkingCheckInResponse(ParkingSession parkingSession);

    ParkingSessionResponse toParkingSessionResponse(ParkingSession parkingSession);

    ParkingCheckOutResponse toParkingCheckOutResponse(ParkingSession parkingSession);
}
