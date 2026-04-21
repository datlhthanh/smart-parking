package com.smartparking.dto.request;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleTypeRequest {
    String name;

    BigDecimal basePrice;
}
