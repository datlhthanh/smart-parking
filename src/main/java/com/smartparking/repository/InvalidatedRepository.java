package com.smartparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartparking.entity.InvalidatedToken;

public interface InvalidatedRepository extends JpaRepository<InvalidatedToken, String> {}
