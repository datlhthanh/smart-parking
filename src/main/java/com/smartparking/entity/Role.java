package com.smartparking.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity {

    @Id
    @Column(nullable = false, unique = true, length = 50)
    String name; // "USER", "ADMIN",...

    @Column(length = 255)
    String description;
}
