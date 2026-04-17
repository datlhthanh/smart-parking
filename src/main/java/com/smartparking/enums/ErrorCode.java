package com.smartparking.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    REJECT_REASON_REQUIRED(2003, "Reject reason required", HttpStatus.BAD_REQUEST),
    INVALID_OTP(9001, "Invalid OTP", HttpStatus.BAD_REQUEST),
    EXPIRED_OTP(9002, "OTP expired", HttpStatus.BAD_REQUEST),

    EMAIL_EXISTED(1001, "Email existed", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(1002, "Phone number existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not found", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS(1004, "Invalid credentials", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    ROLE_NOT_FOUND(1006, "Role not found", HttpStatus.NOT_FOUND),

    VEHICLE_TYPE_NOT_FOUND(2001, "Vehicle type not found", HttpStatus.NOT_FOUND),
    VEHICLE_NOT_FOUND(2002, "Vehicle not found", HttpStatus.NOT_FOUND),
    VEHICLE_NOT_PENDING(2003, "Vehicle not pending", HttpStatus.CONFLICT),
    ;

    // Constructor để gán giá trị cho từng phần tử enum
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    int code;
    String message;
    HttpStatusCode statusCode;
}
