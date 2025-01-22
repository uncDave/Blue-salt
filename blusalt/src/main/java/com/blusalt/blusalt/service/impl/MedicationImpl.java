package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.service.JpaService.MedicationJPAService;
import com.blusalt.blusalt.service.MedicationService;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createFailureResponse;
import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createSuccessResponse;


@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationImpl implements MedicationService {

    private final MedicationJPAService medicationJPAService;

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
                        .body(createSuccessResponse("", "no drone"));
            }

            CreateMedication.GetMedication.Response response = new CreateMedication.GetMedication.Response();
            response.setMedication(all.getContent());
            response.setPage(all.getTotalPages());
            response.setTotalPages(all.getTotalPages());
            response.setTotalDrones(all.getTotalElements());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(response, "all medications retrieved"));
        }catch (IllegalArgumentException e) {
            log.warn("error while getting all drones");
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
                        .body(createFailureResponse("",""));
            }
            Medication medication = optionalMedication.get();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(medication, "medication retrieved"));

        }catch (IllegalArgumentException e) {
            log.warn("error while getting drone with id: {}",id);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }


}
