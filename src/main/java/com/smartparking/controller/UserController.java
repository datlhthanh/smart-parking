package com.smartparking.controller;

import com.smartparking.dto.request.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.smartparking.dto.request.UserCreationRequest;
import com.smartparking.dto.response.ApiResponse;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        var result = userService.getMyInfo();

        return ApiResponse.<UserResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        log.info("Auth: {}", authentication);
        log.info("Authorities: {}", authentication.getAuthorities());
        log.info("Principal: {}", authentication.getPrincipal());

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }


    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){

        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }
}
