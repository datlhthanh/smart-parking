package com.smartparking.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartparking.enums.VehicleRegistrationStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleResponse {
    Long vehicleId;

    String licensePlate;

    VehicleRegistrationStatus status;

    UserResponse user;

    VehicleTypeResponse vehicleType;

    String rejectReason;

    LocalDateTime approvedAt;
}
