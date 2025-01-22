package com.blusalt.blusalt.service;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface MedicationService {
    ResponseEntity<ApiResponse<?>> getAll(CreateMedication.GetMedication getMedication);
    ResponseEntity<ApiResponse<?>> getMedicationById(UUID id);
//    ResponseEntity<ApiResponse<?>> getMedicationByCode(String serialNumber);




}
