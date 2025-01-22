package com.blusalt.blusalt.service.JpaService;

import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MedicationJPAService {
    Optional<Medication> findById(UUID id);
    Optional<Medication> findByCode(String code);
    Medication saveMedication(Medication medication);
    Page<Medication> findAll(Pageable pageable);
}
