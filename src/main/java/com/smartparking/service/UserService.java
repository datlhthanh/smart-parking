package com.smartparking.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.smartparking.dto.request.UserCreationRequest;
import com.smartparking.dto.request.UserUpdateRequest;
import com.smartparking.dto.response.UserResponse;
import com.smartparking.entity.Role;
import com.smartparking.entity.User;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.RoleName;
import com.smartparking.enums.UserStatus;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.UserMapper;
import com.smartparking.repository.RoleRepository;
import com.smartparking.repository.UserRepository;
import com.smartparking.service.validator.UserValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    UserMapper userMapper;

    UserValidator userValidator;

    PasswordEncoder passwordEncoder;

    // @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        userValidator.checkEmailAndPhoneExists(request.getEmail(), request.getPhoneNumber());

        Role role = roleRepository
                .findByName(RoleName.USER.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(Set.of(role));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    // @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);

        if (StringUtils.hasText(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (StringUtils.hasText(request.getPhoneNumber())) {
            userValidator.checkPhoneExists(request.getPhoneNumber());
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    // @PostAuthorize("hasRole('ADMIN') or returnObject.email == authentication.name")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }
}
