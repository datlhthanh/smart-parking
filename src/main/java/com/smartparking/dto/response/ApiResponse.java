package com.smartparking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) // bỏ qua các giá trị null khi trả về JSON
public class ApiResponse<T> {

    @Builder.Default
    int code = 1000;

    String message;

    T result;
}
