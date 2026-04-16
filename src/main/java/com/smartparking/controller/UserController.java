package com.smartparking.controller;

import com.smartparking.dto.request.UserUpdateRequest;
import org.springframework.web.bind.annotation.*;

import com.smartparking.dto.request.UserCreationRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping()
    ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        var result = userService.createUser(request);

        return ApiResponse.<UserResponse>builder()
                .message("User created successfully.")
                .result(result)
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        var result = userService.updateUser(userId, request);

        return ApiResponse.<UserResponse>builder()
                .message("User updated successfully.")
                .result(result)
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);

        return ApiResponse.<Void>builder().message("User deleted successfully.").build();
    }
}
