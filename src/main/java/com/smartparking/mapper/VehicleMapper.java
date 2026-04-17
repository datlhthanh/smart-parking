package com.smartparking.mapper;

import org.mapstruct.*;

import com.smartparking.dto.request.VehicleRequest;
import com.smartparking.dto.response.VehicleResponse;
import com.smartparking.entity.Vehicle;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, VehicleTypeMapper.class}, // tái sử dụng các Mapper đã có
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VehicleMapper {
    Vehicle toVehicle(VehicleRequest request);

    // MapStruct sẽ tự động gọi UserMapper.toUserResponse và VehicleTypeMapper.toVehicleTypeResponse
    VehicleResponse toVehicleResponse(Vehicle vehicle);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVehicle(@MappingTarget Vehicle vehicle, VehicleRequest request);
}
