package com.smartparking.configuration;

import com.smartparking.entity.Role;
import com.smartparking.entity.User;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.RoleName;
import com.smartparking.enums.UserStatus;
import com.smartparking.exception.AppException;
import com.smartparking.repository.RoleRepository;
import com.smartparking.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(
            RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(Role.builder()
                        .name("USER")
                        .description("Role for normal user")
                        .build());
            }
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(Role.builder()
                        .name("ADMIN")
                        .description("Role for admin user")
                        .build());
            }
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Role adminRole = roleRepository.findByName(RoleName.ADMIN.name())
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

                userRepository.save(User.builder()
                        .email("admin@gmail.com")
                        .fullName("Admin")
                        .password(passwordEncoder.encode("admin@123"))
                        .phoneNumber("0123456789")
                        .roles(Set.of(adminRole))
                        .status(UserStatus.ACTIVE)
                        .build());
            }
        };
    }
}
