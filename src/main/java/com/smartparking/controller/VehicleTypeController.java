package com.smartparking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.smartparking.dto.request.VehicleTypeRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.VehicleTypeResponse;
import com.smartparking.service.VehicleTypeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/vehicle-types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VehicleTypeController {
    VehicleTypeService vehicleTypeService;

    @PostMapping
    public ApiResponse<VehicleTypeResponse> createVehicleType(@RequestBody VehicleTypeRequest request) {
        var result = vehicleTypeService.createVehicleType(request);

        return ApiResponse.<VehicleTypeResponse>builder()
                .message("Vehicle type created")
                .result(result)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<VehicleTypeResponse> updateVehicleType(
            @PathVariable Long id, @RequestBody VehicleTypeRequest request) {
        var result = vehicleTypeService.updateVehicleType(id, request);

        return ApiResponse.<VehicleTypeResponse>builder()
                .message("Vehicle type updated")
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVehicleType(@PathVariable Long id) {
        vehicleTypeService.deleteVehicleType(id);

        return ApiResponse.<Void>builder().message("Vehicle type deleted").build();
    }

    @GetMapping
    public ApiResponse<List<VehicleTypeResponse>> getAllVehicleTypes() {
        var result = vehicleTypeService.getAllVehicleTypes();

        return ApiResponse.<List<VehicleTypeResponse>>builder()
                .message("Vehicle type list retrieved")
                .result(result)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<VehicleTypeResponse> getVehicleType(@PathVariable Long id) {
        var result = vehicleTypeService.getVehicleType(id);

        return ApiResponse.<VehicleTypeResponse>builder()
                .message("Vehicle type retrieved")
                .result(result)
                .build();
    }
}
