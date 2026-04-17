package com.smartparking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.smartparking.dto.request.RejectRequest;
import com.smartparking.dto.request.VehicleRequest;
import com.smartparking.dto.response.VehicleResponse;
import com.smartparking.entity.User;
import com.smartparking.entity.Vehicle;
import com.smartparking.entity.VehicleType;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.VehicleRegistrationStatus;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.VehicleMapper;
import com.smartparking.repository.UserRepository;
import com.smartparking.repository.VehicleRepository;
import com.smartparking.repository.VehicleTypeRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VehicleService {
    VehicleRepository vehicleRepository;
    UserRepository userRepository;
    VehicleTypeRepository vehicleTypeRepository;

    VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponse createVehicle(VehicleRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        VehicleType vehicleType = vehicleTypeRepository
                .findById(request.getVehicleTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_TYPE_NOT_FOUND));

        Vehicle vehicle = vehicleMapper.toVehicle(request);
        vehicle.setUser(user);
        vehicle.setVehicleType(vehicleType);
        vehicle.setStatus(VehicleRegistrationStatus.PENDING);

        return vehicleMapper.toVehicleResponse(vehicleRepository.save(vehicle));
    }

    @Transactional
    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
        Vehicle vehicle =
                vehicleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        vehicleMapper.updateVehicle(vehicle, request);

        // chỉ cập nhật lại userId nếu có gửi userId mới
        if (StringUtils.hasText(request.getUserId())) {
            User user = userRepository
                    .findById(request.getUserId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            vehicle.setUser(user);
        }

        // chỉ lại vehicleTypeId cập nhật nếu có gửi vehicleTypeId mới
        if (request.getVehicleTypeId() != null) {
            VehicleType vehicleType = vehicleTypeRepository
                    .findById(request.getVehicleTypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_TYPE_NOT_FOUND));
            vehicle.setVehicleType(vehicleType);
        }

        vehicle.setStatus(VehicleRegistrationStatus.PENDING);
        vehicle.setRejectReason(null);
        vehicle.setApprovedAt(null);

        return vehicleMapper.toVehicleResponse(vehicleRepository.save(vehicle));
    }

    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::toVehicleResponse)
                .toList();
    }

    public VehicleResponse getVehicle(Long id) {
        return vehicleMapper.toVehicleResponse(
                vehicleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND)));
    }

    @Transactional
    public VehicleResponse approveVehicle(Long id) {
        Vehicle vehicle =
                vehicleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        if (vehicle.getStatus() != VehicleRegistrationStatus.PENDING) {
            throw new AppException(ErrorCode.VEHICLE_NOT_PENDING);
        }

        vehicle.setStatus(VehicleRegistrationStatus.APPROVED);
        vehicle.setApprovedAt(LocalDateTime.now());
        vehicle.setRejectReason(null);

        return vehicleMapper.toVehicleResponse(vehicleRepository.save(vehicle));
    }

    @Transactional
    public VehicleResponse rejectVehicle(Long id, RejectRequest request) {
        Vehicle vehicle =
                vehicleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        if (vehicle.getStatus() != VehicleRegistrationStatus.PENDING) {
            throw new AppException(ErrorCode.VEHICLE_NOT_PENDING);
        }

        vehicle.setStatus(VehicleRegistrationStatus.REJECTED);
        vehicle.setApprovedAt(null);

        if (!StringUtils.hasText(request.getReason())) {
            throw new AppException(ErrorCode.REJECT_REASON_REQUIRED);
        }

        vehicle.setRejectReason(request.getReason());

        return vehicleMapper.toVehicleResponse(vehicleRepository.save(vehicle));
    }

    public List<VehicleResponse> getAllVehiclesPending() {
        return vehicleRepository.findAllByStatus(VehicleRegistrationStatus.PENDING).stream()
                .map(vehicleMapper::toVehicleResponse)
                .toList();
    }
}
