package com.smartparking.dto.response;

import java.math.BigDecimal;
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
public class ParkingSessionResponse {
    Long parkingSessionId;

    String licensePlate;

    ParkingSessionStatus status;

    VehicleResponse vehicle;

    ParkingSlotResponse parkingSlot;

    LocalDateTime checkInTime;

    LocalDateTime checkOutTime;

    BigDecimal totalFee;

    String checkInImage;

    String checkOutImage;
}
