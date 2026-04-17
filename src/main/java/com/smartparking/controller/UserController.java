package com.smartparking.controller;

import com.smartparking.dto.request.UserCreationRequest;
import com.smartparking.dto.request.UserUpdateRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping()
    ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        var result = userService.createUser(request);

        return ApiResponse.<UserResponse>builder()
                .message("User created")
                .result(result)
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        var result = userService.updateUser(id, request);

        return ApiResponse.<UserResponse>builder()
                .message("User updated")
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);

        return ApiResponse.<Void>builder().message("User deleted").build();
    }

    @GetMapping("/me")
    ApiResponse<UserResponse> getMyInfo() {
        var result = userService.getMyInfo();

        return ApiResponse.<UserResponse>builder()
                .message("User info retrieved")
                .result(result)
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .message("User list retrieved")
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .message("User info retrieved")
                .result(userService.getUser(id))
                .build();
    }
}
