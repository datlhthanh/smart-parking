package com.smartparking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@MappedSuperclass // định nghĩa một class cha chứa các field dùng chung cho các entity khác
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {

    @CreationTimestamp // auto set khi insert, không update
    @Column(name = "created_at", updatable = false) // map với created_at, không update field
    LocalDateTime createdAt;

    @UpdateTimestamp // auto set khi insert và update
    @Column(name = "updated_at") // map với updated_at
    LocalDateTime updatedAt;
}
