package com.smartparking.mapper;

import org.mapstruct.Mapper;

import com.smartparking.dto.response.RoleResponse;
import com.smartparking.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);
}
