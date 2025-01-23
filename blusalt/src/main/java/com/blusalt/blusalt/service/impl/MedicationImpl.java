package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.drone.UpdateDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.dto.medication.UpdateMedicationDTO;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.service.JpaService.MedicationJPAService;
import com.blusalt.blusalt.service.MedicationService;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createFailureResponse;
import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createSuccessResponse;


@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationImpl implements MedicationService {

    private final MedicationJPAService medicationJPAService;
    private final Cloudinary cloudinary;

    @Override
    public ResponseEntity<ApiResponse<?>> getAll(CreateMedication.GetMedication getMedication) {
        try {
            log.info("Query: {}", getMedication );
            Sort.Direction direction = getMedication.getOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sortBy = Sort.by(direction, getMedication.getSortBy());
            Pageable pageable = PageRequest.of(getMedication.getPage(), getMedication.getLimit(), sortBy);

            Page<Medication> all = medicationJPAService.findAll(pageable);
            if (all.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(createSuccessResponse("", "no medication"));
            }

            CreateMedication.GetMedication.Response response = new CreateMedication.GetMedication.Response();
            response.setMedication(all.getContent());
            response.setPage(all.getTotalPages());
            response.setTotalPages(all.getTotalPages());
            response.setTotalMedication(all.getTotalElements());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(response, "all medications retrieved"));
        }catch (IllegalArgumentException e) {
            log.warn("error while getting all medications");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getMedicationById(UUID id) {
        try {
            Optional<Medication> optionalMedication = medicationJPAService.findById(id);
            if (optionalMedication.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(createFailureResponse("","medication not found"));
            }
            Medication medication = optionalMedication.get();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(medication, "medication retrieved"));

        }catch (IllegalArgumentException e) {
            log.warn("error while getting medication with id: {}",id);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> uploadMedicationImage(MultipartFile multipartFile) {
        String receiptUrl = null;
        try {
            if (multipartFile != null && !multipartFile.isEmpty()){
                Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                        ObjectUtils.asMap(
                                "public_id","",
                                "folder", "images",
                                "overwrite", true,
                                "resource_type", "auto"
                        ));
                receiptUrl = uploadResult.get("secure_url").toString();

            }
            OnboardDroneDTO.logoUploadResponse uploadResponse = OnboardDroneDTO.logoUploadResponse.builder().logoUrl(receiptUrl).build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(uploadResponse,"Logo Successfully uploaded"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createFailureResponse("invalid request", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> updateMedication(UUID medicationId, UpdateMedicationDTO.UpdateMedicationRequest request) {
        try {
            Optional<Medication> optionalMedication = medicationJPAService.findById(medicationId);
            if (optionalMedication.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(createFailureResponse("","medication not found"));
            }
            Medication existingMedication = optionalMedication.get();
            if (request.getName() != null) {
                existingMedication.setName(request.getName());
            }
            if (request.getWeight() != null) {
                existingMedication.setWeight(request.getWeight());
            }
            if (request.getCode() != null) {
                existingMedication.setCode(request.getCode());
            }
            if (request.getLogoUrl() != null) {
                existingMedication.setLogoUrl(request.getLogoUrl());
            }
            UpdateMedicationDTO.UpdateDroneResponse response = UpdateMedicationDTO.UpdateDroneResponse.builder().code(existingMedication.getCode()).build();


            Medication medication = medicationJPAService.saveMedication(existingMedication);
            return ResponseEntity.ok(createSuccessResponse(response, "medication updated successfully"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
