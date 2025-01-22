package com.blusalt.blusalt.repository;

import com.blusalt.blusalt.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {

    Optional<Medication> findByCode(String code);
}
