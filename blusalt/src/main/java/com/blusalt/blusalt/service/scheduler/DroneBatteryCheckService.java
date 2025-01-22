package com.blusalt.blusalt.service.scheduler;


import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.service.DroneAuditTrailService;
import com.blusalt.blusalt.service.DroneService;
import com.blusalt.blusalt.service.JpaService.DroneJPAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DroneBatteryCheckService {

    private final DroneAuditTrailService droneAuditTrailService;
    private final DroneJPAService droneJPAService;


    @Async("taskExecutor")
    @Scheduled(fixedRate = 120000)
    public void checkDroneBatteryLevels() {
        log.info("Scheduled task started to check drone battery levels");

        List<Drone> drones = droneJPAService.findAll();
        for (Drone drone : drones) {
            droneAuditTrailService.logBatteryLevel(drone.getSerialNumber(), drone.getBatteryPercentage());
            log.info("Logged battery level for drone: {}", drone.getSerialNumber());
        }
        log.info("Scheduled task completed");
    }
}
