package com.blusalt.blusalt.controller.drone;


import com.blusalt.blusalt.dto.auth.LoginDTO;
import com.blusalt.blusalt.dto.drone.LoadDroneDTO;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.drone.UpdateDroneDTO;
import com.blusalt.blusalt.service.AdminService;
import com.blusalt.blusalt.service.AuthService;
import com.blusalt.blusalt.service.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createFailureResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drone")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class DroneController {

    private final AdminService adminService;
    private final DroneService droneService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDrone(@RequestBody OnboardDroneDTO.Request request) {
        log.info("create drone: {} ", request.getSerialNumber());
        return adminService.onboardDrone(request);
    }


//    @PostMapping("/load")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> loadDrone(@RequestBody LoadDroneDTO.Request request, @RequestHeader("Action") String action) {
//        log.info("load drone: {} ", request.getDroneId());
//        return adminService.loadDrone(request);
//    }

    @PostMapping("/manage-medications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> manageMedications(@RequestBody LoadDroneDTO.Request request, @RequestHeader("Action") String action) {
        log.info("Managing medications for drone: {} with action: {}", request.getDroneId(), action);

        if (!"LOAD".equalsIgnoreCase(action) && !"REMOVE".equalsIgnoreCase(action)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createFailureResponse("", "Invalid action provided"));
        }

        if ("LOAD".equalsIgnoreCase(action)) {
            return adminService.loadDrone(request);
        } else if ("REMOVE".equalsIgnoreCase(action)) {
            return adminService.removeMedicationsFromDrone(request);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createFailureResponse("", "Invalid action provided"));
        }
    }


    @GetMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> allDrone( @Valid OnboardDroneDTO.GetDrone request) {
        log.info("get all drones: {} ", request.getLimit());
        return droneService.getAll(request);
    }

    @PostMapping("/{droneId}/available-for-loading")
    public ResponseEntity<?> makeDroneAvailableForLoading(@PathVariable UUID droneId) {
        log.info("make drone available : {} ", droneId);
        return adminService.makeDroneAvailableForLoading(droneId);
    }


    @GetMapping("/available")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> allAvailableDrone( @Valid OnboardDroneDTO.GetDrone request) {
        log.info("get all: {} ", request.getLimit());
        return droneService.dronesAvailableForLoading(request);
    }

    @GetMapping("/Id/{uuid}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById( @PathVariable UUID uuid) {
        log.info("get drones by id: {} ", uuid);
        return droneService.getDroneById(uuid);
    }

    @GetMapping("/battery/{uuid}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBattery( @PathVariable UUID uuid) {
        log.info("get drones battery by id: {} ", uuid);
        return droneService.getDroneBatteryLevel(uuid);
    }

    @GetMapping("/drone-items")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getItemsOnDrone( @RequestParam UUID uuid) {
        log.info("get item on drone with id: {} ", uuid);
        return adminService.getItemsLoadedOnDrone(uuid);
    }

    @GetMapping("/serial-no/{serialNumber}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBySerialNumber( @PathVariable String serialNumber) {
        log.info("get drones by serial number: {} ", serialNumber);
        return droneService.getDroneBySerialNumber(serialNumber);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDrone(@PathVariable UUID id, @Valid @RequestBody UpdateDroneDTO.UpdateDroneRequest updateDroneDTO) {
       return droneService.updateDrone(id,updateDroneDTO);
    }
}
