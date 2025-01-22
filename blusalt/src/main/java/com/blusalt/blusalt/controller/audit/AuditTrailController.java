package com.blusalt.blusalt.controller.audit;


import com.blusalt.blusalt.dto.auditTrail.AuditTrail;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.service.AuditTrailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/audit")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class AuditTrailController {

    private final AuditTrailService auditTrailService;

    @GetMapping("/")
    public ResponseEntity<?> allDroneLogs(@Valid AuditTrail.GetAuditTrail request) {
        log.info("get all drones: {} ", request.getLimit());
        return auditTrailService.getAll(request);
    }

}
