package com.smartparking.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleRequest {
    String licensePlate;

    String userId;

    Long vehicleTypeId;
}
