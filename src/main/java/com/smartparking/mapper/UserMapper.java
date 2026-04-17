package com.smartparking.mapper;

import org.mapstruct.*;

import com.smartparking.dto.request.RegisterRequest;
import com.smartparking.dto.request.UserCreationRequest;
import com.smartparking.dto.request.UserUpdateRequest;
import com.smartparking.dto.response.RegisterResponse;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toRegisterUser(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
