package com.smartparking.dto.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterResponse {
    String userId;

    String fullName;

    String email;

    String phoneNumber;

    Set<RoleResponse> roles;
}
