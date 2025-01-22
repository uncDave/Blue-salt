package com.blusalt.blusalt.service.JpaService.impl;

import com.blusalt.blusalt.entity.DroneAuditTrail;
import com.blusalt.blusalt.repository.DroneAuditTrailRepository;
import com.blusalt.blusalt.service.JpaService.DroneAuditTrailJPAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class DroneAuditTrailJPAImpl implements DroneAuditTrailJPAService {

    private final DroneAuditTrailRepository droneAuditTrailRepository;

    @Override
    public Page<DroneAuditTrail> findAll(Pageable pageable) {
        return droneAuditTrailRepository.findAll(pageable);
    }

    @Override
    public void saveAuditEvent(DroneAuditTrail event) {
         droneAuditTrailRepository.save(event);
    }
}
