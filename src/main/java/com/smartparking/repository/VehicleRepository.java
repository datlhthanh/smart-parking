package com.smartparking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartparking.entity.Vehicle;
import com.smartparking.enums.VehicleRegistrationStatus;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByStatus(VehicleRegistrationStatus status);
}
