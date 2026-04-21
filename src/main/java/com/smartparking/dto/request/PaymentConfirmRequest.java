package com.smartparking.dto.request;

import com.smartparking.enums.PaymentMethod;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentConfirmRequest {
    Long paymentId;

    PaymentMethod method;

    String transactionNo;
}
