package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.service.DroneAuditTrailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneAuditTrailServiceImpl implements DroneAuditTrailService {
    @Override
    public void logBatteryLevel(String droneSerialNumber, double batteryPercentage) {

    }
}
