package com.blusalt.blusalt.service.JpaService.impl;

import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.repository.MedicationRepository;
import com.blusalt.blusalt.service.JpaService.MedicationJPAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MedicationJPAImpl implements MedicationJPAService {

    private final MedicationRepository medicationRepository;

    @Override
    public Optional<Medication> findById(UUID id) {
      return medicationRepository.findById(id);
    }

    @Override
    public Optional<Medication> findByCode(String code) {
        return medicationRepository.findByCode(code);
    }

    @Override
    public Medication saveMedication(Medication medication) {
      return medicationRepository.save(medication);
    }

    @Override
    public Page<Medication> findAll(Pageable pageable) {
        return medicationRepository.findAll(pageable);
    }
}
