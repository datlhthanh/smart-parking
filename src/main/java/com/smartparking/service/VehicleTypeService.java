package com.smartparking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartparking.dto.request.VehicleTypeRequest;
import com.smartparking.dto.response.VehicleTypeResponse;
import com.smartparking.entity.VehicleType;
import com.smartparking.enums.ErrorCode;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.VehicleTypeMapper;
import com.smartparking.repository.VehicleTypeRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VehicleTypeService {
    VehicleTypeRepository vehicleTypeRepository;

    VehicleTypeMapper vehicleTypeMapper;

    @Transactional
    public VehicleTypeResponse createVehicleType(VehicleTypeRequest request) {
        VehicleType vehicleType = vehicleTypeMapper.toVehicleType(request);

        return vehicleTypeMapper.toVehicleTypeResponse(vehicleTypeRepository.save(vehicleType));
    }

    @Transactional
    public VehicleTypeResponse updateVehicleType(Long id, VehicleTypeRequest request) {
        VehicleType vehicleType = vehicleTypeRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_TYPE_NOT_FOUND));

        vehicleTypeMapper.updateVehicleType(vehicleType, request);

        return vehicleTypeMapper.toVehicleTypeResponse(vehicleTypeRepository.save(vehicleType));
    }

    @Transactional
    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteById(id);
    }

    public List<VehicleTypeResponse> getAllVehicleTypes() {
        return vehicleTypeRepository.findAll().stream()
                .map(vehicleTypeMapper::toVehicleTypeResponse)
                .toList();
    }

    public VehicleTypeResponse getVehicleType(Long id) {
        return vehicleTypeMapper.toVehicleTypeResponse(vehicleTypeRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_TYPE_NOT_FOUND)));
    }
}
