package com.smartparking.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartparking.entity.ParkingSlot;
import com.smartparking.entity.Vehicle;
import com.smartparking.enums.ParkingSessionStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingCheckInResponse {
    Long parkingSessionId;
    LocalDateTime checkInTime;
    String licensePlate;
    String checkInImage;
    Vehicle vehicle;
    ParkingSlot parkingSlot;
    ParkingSessionStatus status;
}
