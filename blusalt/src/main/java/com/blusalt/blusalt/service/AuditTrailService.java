package com.blusalt.blusalt.service;

import com.blusalt.blusalt.dto.auditTrail.AuditTrail;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuditTrailService {
    ResponseEntity<ApiResponse<?>> getAll(AuditTrail.GetAuditTrail getAuditTrail);
}
