package com.blusalt.blusalt.service;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.dto.medication.UpdateMedicationDTO;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MedicationService {
    ResponseEntity<ApiResponse<?>> getAll(CreateMedication.GetMedication getMedication);
    ResponseEntity<ApiResponse<?>> getMedicationById(UUID id);
    ResponseEntity<ApiResponse<?>> uploadMedicationImage(MultipartFile multipartFile);
    ResponseEntity<ApiResponse<?>> updateMedication(UUID medicationId, UpdateMedicationDTO.UpdateMedicationRequest request);
//    ResponseEntity<ApiResponse<?>> getMedicationByCode(String serialNumber);




}
