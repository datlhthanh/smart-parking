package com.smartparking.dto.response;

import java.math.BigDecimal;

import com.smartparking.enums.PaymentMethod;
import com.smartparking.enums.PaymentStatus;
import com.smartparking.enums.PaymentType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    Long paymentId;

    PaymentType type;

    BigDecimal amount;

    PaymentMethod method;

    PaymentStatus status;

    String transactionNo;

    UserResponse user;
}
