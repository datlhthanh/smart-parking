package com.smartparking.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import com.smartparking.enums.UserStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity // đánh dấu đây là entity, sẽ map với bảng trong DB
@Table(name = "users") // map với bảng users
@Getter // tự động sinh các getter
@Setter // tự động sinh các setter
@NoArgsConstructor // lombok: sinh constructor không tham số
@AllArgsConstructor // lombok: sinh constructor có tham số cho tất cả field
@Builder // lombok: hỗ trợ tạo object theo Builder pattern
@FieldDefaults(level = AccessLevel.PRIVATE) // lombok: tất cả field mặc định là private, không cần khai báo lại
public class User extends BaseEntity {

    @Id
    // sử dụng UUID làm ID, tự động sinh giá trị khi tạo mới
    @GeneratedValue(strategy = GenerationType.UUID)
    // không cập nhật, không null, độ dài tối đa 36 ký tự
    @Column(updatable = false, nullable = false, length = 36)
    String userId;

    @Column(nullable = false, length = 100)
    String fullName;

    @Column(unique = true, nullable = false, length = 10)
    String phoneNumber;

    @Column(unique = true, nullable = false, length = 100)
    String email;

    @Column(nullable = false, length = 255)
    String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    UserStatus status = UserStatus.ACTIVE; // Enum: ACTIVE, INACTIVE, BANNED

    // cấu hình ManyToMany
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();
}
