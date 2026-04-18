package com.smartparking.controller;

import com.smartparking.dto.request.ParkingCheckInRequest;
import com.smartparking.dto.request.ParkingCheckOutRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.ParkingCheckInResponse;
import com.smartparking.dto.response.ParkingCheckOutResponse;
import com.smartparking.dto.response.ParkingSessionResponse;
import com.smartparking.service.ParkingSessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-sessions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParkingSessionController {

    ParkingSessionService parkingSessionService;

    @PostMapping("/check-in")
    public ApiResponse<ParkingCheckInResponse> checkIn(@RequestBody ParkingCheckInRequest request) {
        var result = parkingSessionService.checkIn(request);

        return ApiResponse.<ParkingCheckInResponse>builder().result(result).build();
    }

    @PostMapping("/check-out")
    public ApiResponse<ParkingCheckOutResponse> checkOut(@RequestBody ParkingCheckOutRequest request) {
        var result = parkingSessionService.checkOut(request);

        return ApiResponse.<ParkingCheckOutResponse>builder().result(result).build();
    }

    @GetMapping
    public ApiResponse<List<ParkingSessionResponse>> getAllParkingSessions() {
        var result = parkingSessionService.getAllParkingSessions();

        return ApiResponse.<List<ParkingSessionResponse>>builder()
                .message("Parking sessions list retrieved")
                .result(result)
                .build();
    }
}
