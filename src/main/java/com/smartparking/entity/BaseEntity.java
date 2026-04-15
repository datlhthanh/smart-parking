package com.smartparking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass // định nghĩa một class cha chứa các field dùng chung cho các entity khác
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // kêu JPA dùng Auto-increment
    Long id;

    @CreationTimestamp // auto set khi insert, không update
    @Column(name = "created_at", updatable = false) // map với created_at, không update field
    LocalDateTime createdAt;

    @UpdateTimestamp // auto set khi insert và update
    @Column(name = "updated_at") // map với updated_at
    LocalDateTime updatedAt;
}
