package com.smartparking.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    String licensePlate;

    ParkingSessionStatus status;

    VehicleResponse vehicle;

    ParkingSlotResponse parkingSlot;

    LocalDateTime checkInTime;

    String checkInImage;
}
