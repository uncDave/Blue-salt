package com.blusalt.blusalt.repository;

import com.blusalt.blusalt.entity.DroneAuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DroneAuditTrailRepository extends JpaRepository<DroneAuditTrail, UUID> {
//    Page<AuditLog> findAll(Pageable pageable);
}
