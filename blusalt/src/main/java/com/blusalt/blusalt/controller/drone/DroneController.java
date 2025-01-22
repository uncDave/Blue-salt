package com.blusalt.blusalt.controller.drone;


import com.blusalt.blusalt.dto.auth.LoginDTO;
import com.blusalt.blusalt.dto.drone.LoadDroneDTO;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.service.AdminService;
import com.blusalt.blusalt.service.AuthService;
import com.blusalt.blusalt.service.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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


    @PostMapping("/load")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> loadDrone(@RequestBody LoadDroneDTO.Request request) {
        log.info("load drone: {} ", request.getDroneId());
        return adminService.loadDrone(request);
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
}
