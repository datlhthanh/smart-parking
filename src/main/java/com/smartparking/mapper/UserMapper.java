package com.smartparking.mapper;

import org.mapstruct.Mapper;

import com.smartparking.dto.request.RegisterRequest;
import com.smartparking.dto.response.RegisterResponse;
import com.smartparking.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toRegisterUser(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);
}
