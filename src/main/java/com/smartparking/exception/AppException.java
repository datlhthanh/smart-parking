package com.smartparking.exception;

import com.smartparking.enums.ErrorCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private ErrorCode errorCode; // lưu mã lỗi để controller advice xử lý

    // khi tạo AppException, truyền vào ErrorCode
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // gọi constructor của RuntimeException với message từ ErrorCode
        this.errorCode = errorCode;
    }

    //    // getter: lấy ra errorCode (để controller advice biết lỗi gì)
    //    public ErrorCode getErrorCode() {
    //        return errorCode;
    //    }
    //
    //    // Setter: set lại errorCode nếu cần (thường ít dùng, vì errorCode nên immutable)
    //    public void setErrorCode(ErrorCode errorCode) {
    //        this.errorCode = errorCode;
    //    }
}
