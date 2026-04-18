package com.smartparking.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartparking.entity.ParkingSlot;
import com.smartparking.enums.ParkingSessionStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingCheckOutResponse {
    Long parkingSessionId;
    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;
    Double totalFee;
    String licensePlate;
    String checkInImage;
    String checkOutImage;
    ParkingSessionStatus status;
    VehicleResponse vehicle;
    ParkingSlot parkingSlot;
}
