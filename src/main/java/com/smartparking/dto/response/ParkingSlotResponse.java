package com.smartparking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartparking.enums.SlotStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingSlotResponse {
    Long parkingSlotId;

    String name;

    SlotStatus status;
}
