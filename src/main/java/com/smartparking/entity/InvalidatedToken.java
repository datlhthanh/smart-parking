package com.smartparking.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "invalidated_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {
    @Id
    @Column(length = 36)
    String id;

    @Column(nullable = false)
    Date expiryTime;
}
