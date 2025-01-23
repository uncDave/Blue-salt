package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.dto.drone.LoadDroneDTO;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.enums.DroneModel;
import com.blusalt.blusalt.enums.DroneState;
import com.blusalt.blusalt.service.AdminService;
import com.blusalt.blusalt.service.JpaService.DroneJPAService;
import com.blusalt.blusalt.service.JpaService.MedicationJPAService;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createFailureResponse;
import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createSuccessResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final DroneJPAService droneJPAService;
    private final MedicationJPAService medicationJPAService;

    @Override
    public ResponseEntity<ApiResponse<?>> onboardDrone(OnboardDroneDTO.Request request) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findBySerialNumber(request.getSerialNumber());
            if (optionalDrone.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(createFailureResponse("", "drone exists"));
            }

            Drone drone = Drone.builder().droneModel(DroneModel.valueOf(request.getModel()))
                    .serialNumber(request.getSerialNumber())
                    .batteryPercentage(request.getBatteryCapacity()).weight(request.getWeight()).droneState(DroneState.IDLE).build();

            Drone savedDrone = droneJPAService.saveDrone(drone);
            log.info("saved drone: {}",savedDrone.getDroneModel());

            OnboardDroneDTO.Response response = OnboardDroneDTO.Response.builder().serialNumber(savedDrone.getSerialNumber()).id(savedDrone.getId()).build();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(response, "drone created"));

        } catch (IllegalArgumentException e) {
            log.warn("error while creating drone");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> CreateMedication(CreateMedication.Request request) {
        try{
            Optional<Medication> optionalMedication = medicationJPAService.findByCode(request.getCode());
            if (optionalMedication.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(createFailureResponse("", "medication with code exists already"));
            }

            Medication medication = Medication.builder().code(request.getCode()).weight(request.getWeight()).name(request.getName()).logoUrl(request.getLogoUrl()).build();

            Medication savedMedication = medicationJPAService.saveMedication(medication);

            CreateMedication.Response response = CreateMedication.Response.builder().Id(savedMedication.getId()).logoUrl(savedMedication.getLogoUrl()).build();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(response, "medication created"));

        }catch (IllegalArgumentException e) {
            log.warn("error while creating medication");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> loadDrone(LoadDroneDTO.Request request) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findById(request.getDroneId());
            if (optionalDrone.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(createFailureResponse("", "drone does not exist"));
            }
            Drone drone = optionalDrone.get();
            log.info("drone:{}", drone.getSerialNumber());
            double droneMaxWeight = drone.getWeight();
            log.info("max weight: {}",droneMaxWeight);
            double currentTotalWeight = drone.getTotalWeightState();
            log.info("current weight:{}",currentTotalWeight);

            List<UUID> medicationIds = request.getMedicationId();;
            for (UUID medicationId : medicationIds) {
                log.info("medicationId: {}",medicationId);
                Optional<Medication> optionalMedication = medicationJPAService.findById(medicationId);
                if (optionalMedication.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(createFailureResponse("", "Medication with ID " + medicationId + " not found"));
                }

                Medication medication = optionalMedication.get();
                log.info("saved meds: {}",medication.getCode());

                if (currentTotalWeight + medication.getWeight() > droneMaxWeight) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(createFailureResponse("", "Drone has reached its maximum weight capacity"));
                }
                log.info("saving.....");
                log.info("drones meds list: {}", drone.getMedications());
                drone.getMedications().add(medication);
                log.info("med weight:{}",medication.getWeight());
                currentTotalWeight += medication.getWeight();

                drone.setTotalWeightState(currentTotalWeight);
                drone.setDroneState(DroneState.LOADED);
            }
            droneJPAService.saveDrone(drone);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse("","Drone loaded with medications successfully"));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("", "An error occurred while loading the drone"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> removeMedicationsFromDrone(LoadDroneDTO.Request request) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findById(request.getDroneId());
            if (optionalDrone.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(createFailureResponse("", "Drone does not exist"));
            }
            Drone drone = optionalDrone.get();
            log.info("drone:{}", drone.getSerialNumber());

            double currentTotalWeight = drone.getTotalWeightState();
            log.info("current weight:{}", currentTotalWeight);

            List<UUID> medicationIds = request.getMedicationId();
            for (UUID medicationId : medicationIds) {
                log.info("Removing medicationId: {}", medicationId);
                Optional<Medication> optionalMedication = medicationJPAService.findById(medicationId);
                if (optionalMedication.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(createFailureResponse("", "Medication with ID " + medicationId + " not found"));
                }

                Medication medication = optionalMedication.get();
                log.info("Found medication: {}", medication.getCode());

                if (!drone.getMedications().contains(medication)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(createFailureResponse("", "Medication not found in drone's medication list"));
                }

                log.info("Removing medication weight: {}", medication.getWeight());
                currentTotalWeight -= medication.getWeight();
                drone.setTotalWeightState(currentTotalWeight);

                drone.getMedications().remove(medication);
            }

            if (drone.getMedications().isEmpty()) {
                drone.setDroneState(DroneState.IDLE);
            }

            droneJPAService.saveDrone(drone);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse("", "Medications removed from the drone successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("", "An error occurred while removing medications from the drone"));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<?>> makeDroneAvailableForLoading(UUID droneId) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findById(droneId);
            if (optionalDrone.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createFailureResponse("", "Drone not found"));
            }

            Drone drone = optionalDrone.get();

            if (drone.getBatteryPercentage() < 25) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createFailureResponse("", "Drone battery must be at 100% before making it available for loading"));
            }

            drone.setDroneState(DroneState.LOADING);
            droneJPAService.saveDrone(drone);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse("", "Drone is now available for loading"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("", "An error occurred while making the drone available for loading"));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<?>> getItemsLoadedOnDrone(UUID id) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findById(id);
            if (optionalDrone.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(createFailureResponse("", "drone does not exist"));
            }
            Drone drone = optionalDrone.get();
            List<Medication> medications = drone.getMedications();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(medications,"Drone items retrieved"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("", "An error occurred while getting the items on the drone"));
        }
    }
}
