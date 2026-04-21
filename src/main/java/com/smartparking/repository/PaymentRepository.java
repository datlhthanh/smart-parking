package com.smartparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartparking.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {}
