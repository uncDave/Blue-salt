package com.blusalt.blusalt.service.JpaService;

import com.blusalt.blusalt.entity.DroneAuditTrail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DroneAuditTrailJPAService {

    Page<DroneAuditTrail> findAll(Pageable pageable);
    void saveAuditEvent(DroneAuditTrail event);
}
