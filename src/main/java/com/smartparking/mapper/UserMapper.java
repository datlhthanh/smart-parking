package com.smartparking.mapper;

import org.mapstruct.Mapper;

import com.smartparking.dto.request.RegisterRequest;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest request);

    UserResponse toUserResponse(User user);
}
