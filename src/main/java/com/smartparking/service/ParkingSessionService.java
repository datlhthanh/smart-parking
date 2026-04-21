package com.smartparking.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartparking.dto.request.ParkingCheckInRequest;
import com.smartparking.dto.request.ParkingCheckOutRequest;
import com.smartparking.dto.response.ParkingCheckInResponse;
import com.smartparking.dto.response.ParkingCheckOutResponse;
import com.smartparking.dto.response.ParkingSessionResponse;
import com.smartparking.entity.ParkingSession;
import com.smartparking.entity.ParkingSlot;
import com.smartparking.entity.Payment;
import com.smartparking.entity.Vehicle;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.ParkingSessionStatus;
import com.smartparking.enums.PaymentStatus;
import com.smartparking.enums.SlotStatus;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.ParkingSessionMapper;
import com.smartparking.repository.ParkingSessionRepository;
import com.smartparking.repository.ParkingSlotRepository;
import com.smartparking.repository.PaymentRepository;
import com.smartparking.repository.VehicleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ParkingSessionService {
    ParkingSessionRepository parkingSessionRepository;
    VehicleRepository vehicleRepository;
    ParkingSlotRepository parkingSlotRepository;
    PaymentRepository paymentRepository;

    ParkingSessionMapper parkingSessionMapper;

    @Transactional
    public ParkingCheckInResponse checkIn(ParkingCheckInRequest request) {
        var licensePlate = request.getLicensePlate();

        boolean exist =
                parkingSessionRepository.existsByLicensePlateAndStatus(licensePlate, ParkingSessionStatus.ACTIVE);

        if (exist) throw new AppException(ErrorCode.VEHICLE_CHECKED);

        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);

        Long vehicleTypeId;

        if (vehicle != null) {
            if (vehicle.getVehicleType() == null) {
                throw new AppException(ErrorCode.VEHICLE_TYPE_NULL);
            }
            vehicleTypeId = vehicle.getVehicleType().getVehicleTypeId();
        } else {
            vehicleTypeId = request.getVehicleTypeId();
        }

        ParkingSlot parkingSlot = parkingSlotRepository
                .findFirstByStatusAndParkingZone_AllowedVehicleType_VehicleTypeId(SlotStatus.AVAILABLE, vehicleTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_AVAILABLE_SLOT));

        ParkingSession parkingSession = parkingSessionMapper.toParkingSession(request);
        parkingSession.setCheckInTime(LocalDateTime.now());
        parkingSession.setVehicle(vehicle);
        parkingSession.setParkingSlot(parkingSlot);
        parkingSession.setStatus(ParkingSessionStatus.ACTIVE);

        parkingSlot.setStatus(SlotStatus.OCCUPIED);
        parkingSlotRepository.save(parkingSlot);

        return parkingSessionMapper.toParkingCheckInResponse(parkingSessionRepository.save(parkingSession));
    }

    @Transactional
    public ParkingCheckOutResponse checkOut(ParkingCheckOutRequest request) {
        var licensePlate = request.getLicensePlate();

        ParkingSession parkingSession = parkingSessionRepository
                .findByLicensePlateAndStatus(licensePlate, ParkingSessionStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.ACTIVE_SESSION_NOT_FOUND));

        parkingSession.setCheckOutTime(LocalDateTime.now());
        parkingSession.setCheckOutImage(request.getCheckOutImage());
        parkingSession.setTotalFee(calculateParkingFee(parkingSession));
        parkingSession.setStatus(ParkingSessionStatus.PENDING_PAYMENT);

        parkingSessionRepository.save(parkingSession);

        Payment payment = Payment.builder()
                .parkingSession(parkingSession)
                .amount(parkingSession.getTotalFee())
                .status(PaymentStatus.PENDING)
                .build();
        paymentRepository.save(payment);

        return parkingSessionMapper.toParkingCheckOutResponse(parkingSession, payment);
    }

    private BigDecimal calculateParkingFee(ParkingSession parkingSession) {
        BigDecimal basePrice = parkingSession
                .getParkingSlot()
                .getParkingZone()
                .getAllowedVehicleType()
                .getBasePrice();
        Duration duration = Duration.between(parkingSession.getCheckInTime(), parkingSession.getCheckOutTime());
        long minutes = Math.max(0, duration.toMinutes());

        // nếu gửi dưới 60 phút thì thu đúng giá gốc
        if (minutes <= 60) {
            return basePrice;
        }

        // nếu lố giờ thì mới tính tiền extra
        BigDecimal extraMinutes = BigDecimal.valueOf(minutes - 60);
        BigDecimal extraFee = extraMinutes
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP)
                .multiply(basePrice);

        return basePrice.add(extraFee);
    }

    public List<ParkingSessionResponse> getAllParkingSessions() {
        return parkingSessionRepository.findAll().stream()
                .map(parkingSessionMapper::toParkingSessionResponse)
                .toList();
    }
}
