package com.smartparking.repository;

import com.smartparking.entity.Vehicle;
import com.smartparking.enums.VehicleRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByStatus(VehicleRegistrationStatus status);
}
