package com.smartparking.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception.", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_EXISTED(1001, "Email already exists.", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(1002, "Phone number already exists.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not found.", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS(1003, "Wrong email or password, try again.", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND(2001, "Role not found.", HttpStatus.NOT_FOUND),
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
