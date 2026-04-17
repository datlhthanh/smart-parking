package com.smartparking.mapper;

import org.mapstruct.*;

import com.smartparking.dto.request.VehicleTypeRequest;
import com.smartparking.dto.response.VehicleTypeResponse;
import com.smartparking.entity.VehicleType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VehicleTypeMapper {

    @Mapping(target = "vehicleTypeId", ignore = true)
    VehicleType toVehicleType(VehicleTypeRequest request);

    VehicleTypeResponse toVehicleTypeResponse(VehicleType vehicleType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVehicleType(@MappingTarget VehicleType vehicleType, VehicleTypeRequest request);
}
