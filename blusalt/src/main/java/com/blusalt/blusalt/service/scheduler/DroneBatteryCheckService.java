package com.blusalt.blusalt.service.scheduler;


import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.service.DroneAuditTrailService;
import com.blusalt.blusalt.service.DroneService;
import com.blusalt.blusalt.service.JpaService.DroneJPAService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneBatteryCheckService {

    private final DroneAuditTrailService droneAuditTrailService;
    private final DroneJPAService droneJPAService;


    @Async("taskExecutor")
//    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "0 */2 * * * *")
    public void checkDroneBatteryLevels() {
        List<Drone> drones = droneJPAService.findAll();
        for (Drone drone : drones) {
            droneAuditTrailService.logBatteryLevel(drone.getSerialNumber(), drone.getBatteryPercentage());
        }
    }
}
