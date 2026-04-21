package com.smartparking.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    String fullName;

    String email;

    String phoneNumber;

    String password;
}
