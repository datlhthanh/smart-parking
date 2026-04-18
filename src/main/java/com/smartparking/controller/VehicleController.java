package com.smartparking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.smartparking.dto.request.VehicleRejectRequest;
import com.smartparking.dto.request.VehicleRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.VehicleResponse;
import com.smartparking.service.VehicleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VehicleController {
    VehicleService vehicleService;

    @PostMapping
    public ApiResponse<VehicleResponse> createVehicle(@RequestBody VehicleRequest request) {
        var result = vehicleService.createVehicle(request);

        return ApiResponse.<VehicleResponse>builder()
                .message("Vehicle created")
                .result(result)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<VehicleResponse> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequest request) {
        var result = vehicleService.updateVehicle(id, request);

        return ApiResponse.<VehicleResponse>builder()
                .message("Vehicle updated")
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);

        return ApiResponse.<Void>builder().message("Vehicle deleted").build();
    }

    @GetMapping
    public ApiResponse<List<VehicleResponse>> getAllVehicles() {
        var result = vehicleService.getAllVehicles();

        return ApiResponse.<List<VehicleResponse>>builder()
                .message("Vehicle list retrieved")
                .result(result)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<VehicleResponse> getVehicle(@PathVariable Long id) {
        var result = vehicleService.getVehicle(id);

        return ApiResponse.<VehicleResponse>builder()
                .message("Vehicle retrieved")
                .result(result)
                .build();
    }

    @PatchMapping("/{id}/approve")
    public ApiResponse<VehicleResponse> approveVehicle(@PathVariable Long id) {
        var result = vehicleService.approveVehicle(id);

        return ApiResponse.<VehicleResponse>builder()
                .message("Vehicle approved")
                .result(result)
                .build();
    }

    @PatchMapping("/{id}/reject")
    public ApiResponse<VehicleResponse> rejectVehicle(
            @PathVariable Long id, @RequestBody VehicleRejectRequest request) {
        var result = vehicleService.rejectVehicle(id, request);

        return ApiResponse.<VehicleResponse>builder()
                .message("Vehicle rejected")
                .result(result)
                .build();
    }

    @GetMapping("/pending")
    public ApiResponse<List<VehicleResponse>> getAllVehiclesPending() {
        var result = vehicleService.getAllVehiclesPending();

        return ApiResponse.<List<VehicleResponse>>builder()
                .message("Vehicle pending status retrieved")
                .result(result)
                .build();
    }
}
