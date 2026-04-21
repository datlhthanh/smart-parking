package com.smartparking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingLotResponse {
    Long parkingLotId;

    String name;

    String address;

    Double latitude;

    Double longitude;

    Boolean isActive;
}
