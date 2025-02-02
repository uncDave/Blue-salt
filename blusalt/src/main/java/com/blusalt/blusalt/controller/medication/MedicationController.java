package com.blusalt.blusalt.controller.medication;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.drone.UpdateDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.dto.medication.UpdateMedicationDTO;
import com.blusalt.blusalt.service.AdminService;
import com.blusalt.blusalt.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medication")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class MedicationController {

    private final AdminService adminService;
    private final MedicationService medicationService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN ')")
    public ResponseEntity<?> createMedication(@RequestBody CreateMedication.Request request) {
        log.info("create medication: {} ", request.getCode());
        return adminService.CreateMedication(request);
    }

    @GetMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> allMedication( @Valid CreateMedication.GetMedication request) {
        log.info("get all: {} ", request.getLimit());
        return medicationService.getAll(request);
    }

    @GetMapping("/Id/{uuid}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById( @PathVariable UUID uuid) {
        log.info("get drones by id: {} ", uuid);
        return medicationService.getMedicationById(uuid);
    }

    @PostMapping("/medication-image")
    public ResponseEntity<?> uploadLogo(@RequestParam MultipartFile file) throws IOException {
//        log.info("This is the title:{}",title);
        log.info("This is the file:{}",file.getOriginalFilename());
        return medicationService.uploadMedicationImage(file);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMedication(@PathVariable UUID id, @Valid @RequestBody UpdateMedicationDTO.UpdateMedicationRequest request) {
        log.info("update...: {}",id);
        return medicationService.updateMedication(id,request);
    }
}
