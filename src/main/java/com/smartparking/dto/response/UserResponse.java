package com.smartparking.dto.response;

import java.util.Set;

import com.smartparking.enums.UserStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String email;
    String password;
    String fullName;
    String phoneNumber;
    UserStatus status;
    Set<RoleResponse> roles;
}
