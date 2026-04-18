package com.smartparking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartparking.entity.ParkingSession;
import com.smartparking.enums.ParkingSessionStatus;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    boolean existsByLicensePlateAndStatus(String licensePlate, ParkingSessionStatus status);

    Optional<ParkingSession> findByLicensePlateAndStatus(String licensePlate, ParkingSessionStatus status);
}
