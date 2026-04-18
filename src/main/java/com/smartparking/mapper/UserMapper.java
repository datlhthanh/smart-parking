package com.smartparking.mapper;

import org.mapstruct.*;

import com.smartparking.dto.request.UserCreateRequest;
import com.smartparking.dto.request.UserRegisterRequest;
import com.smartparking.dto.request.UserUpdateRequest;
import com.smartparking.dto.response.UserRegisterResponse;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(UserRegisterRequest request);

    UserRegisterResponse toRegisterResponse(User user);

    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
