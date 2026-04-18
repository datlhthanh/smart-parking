package com.smartparking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartparking.entity.ParkingSlot;
import com.smartparking.enums.SlotStatus;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    Optional<ParkingSlot> findFirstByStatusAndParkingZone_AllowedVehicleType_VehicleTypeId(
            SlotStatus status, Long parkingZoneAllowedVehicleTypeVehicleTypeId);
}
