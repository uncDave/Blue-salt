package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.entity.DroneAuditTrail;
import com.blusalt.blusalt.service.DroneAuditTrailService;
import com.blusalt.blusalt.service.JpaService.DroneAuditTrailJPAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneAuditTrailServiceImpl implements DroneAuditTrailService {
    private final DroneAuditTrailJPAService droneAuditTrailJPAService;


    @Override
    public void logBatteryLevel(String droneSerialNumber, double batteryPercentage) {
        try {
            DroneAuditTrail auditEvent = new DroneAuditTrail(
                    droneSerialNumber,
                    batteryPercentage
            );
            droneAuditTrailJPAService.saveAuditEvent(auditEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
