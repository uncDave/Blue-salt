package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.drone.UpdateDroneDTO;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.enums.DroneModel;
import com.blusalt.blusalt.enums.DroneState;
import com.blusalt.blusalt.service.DroneService;
import com.blusalt.blusalt.service.JpaService.DroneJPAService;
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
public class DroneImpl implements DroneService {

    private final DroneJPAService droneJPAService;


    @Override
    public ResponseEntity<ApiResponse<?>> getAll(OnboardDroneDTO.GetDrone getDrone) {
        try {
            log.info("Query: {}", getDrone );
            Sort.Direction direction = getDrone.getOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sortBy = Sort.by(direction, getDrone.getSortBy());
            Pageable pageable = PageRequest.of(getDrone.getPage(), getDrone.getLimit(), sortBy);

            Page<Drone> all = droneJPAService.findAll(pageable);
            if (all.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(createSuccessResponse("", "no drone"));
            }

            OnboardDroneDTO.GetDrone.Response response = new OnboardDroneDTO.GetDrone.Response();
            response.setDrones(all.getContent());
            response.setPage(all.getTotalPages());
            response.setTotalPages(all.getTotalPages());
            response.setTotalDrones(all.getTotalElements());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(response, "all drones retrieved"));
        }catch (IllegalArgumentException e) {
            log.warn("error while getting all drones");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<?>> getDroneById(UUID id) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findById(id);
            if (optionalDrone.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(createFailureResponse("","drone does not exist"));
            }
            Drone drone = optionalDrone.get();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(drone, "drone retrieved"));

        }catch (IllegalArgumentException e) {
            log.warn("error while getting drone with id: {}",id);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getDroneBatteryLevel(UUID id) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findById(id);
            if (optionalDrone.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(createFailureResponse("",""));
            }
            Drone drone = optionalDrone.get();

            OnboardDroneDTO.BatteryLevelResponse response = OnboardDroneDTO.BatteryLevelResponse.builder().batteryCapacity(drone.getBatteryPercentage()).build();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(response, "drone retrieved"));

        }catch (IllegalArgumentException e) {
            log.warn("error while getting drone battery with id: {}",id);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> dronesAvailableForLoading(OnboardDroneDTO.GetDrone getDrone) {
        try {
            log.info("Query for all available drones: {}", getDrone);

            Sort.Direction direction = getDrone.getOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sortBy = Sort.by(direction, getDrone.getSortBy());
            Pageable pageable = PageRequest.of(getDrone.getPage(), getDrone.getLimit(), sortBy);

            Page<Drone> drones = droneJPAService.findByDroneState(DroneState.LOADING, pageable);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(drones.getContent(), "Successfully fetched drones available for loading"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("", "An error occurred while fetching drones available for loading"));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<?>> getDroneBySerialNumber(String serialNumber) {
        try {
            Optional<Drone> optionalDrone = droneJPAService.findBySerialNumber(serialNumber);
            if (optionalDrone.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(createFailureResponse("",""));
            }
            Drone drone = optionalDrone.get();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(drone, "drone retrieved"));

        }catch (IllegalArgumentException e) {
            log.warn("error while getting drone with serial number: {}",serialNumber);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> updateDrone(UUID droneId, UpdateDroneDTO.UpdateDroneRequest updateDroneDTO) {
        try {
            // Check if drone exists
            Optional<Drone> optionalDrone = droneJPAService.findById(droneId);
            if (optionalDrone.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)  // Use 404 if drone does not exist
                        .body(createFailureResponse(null, "Drone does not exist"));
            }

            Drone existingDrone = optionalDrone.get();

            if (updateDroneDTO.getSerialNumber() != null) {
                existingDrone.setSerialNumber(updateDroneDTO.getSerialNumber());
            }
            if (updateDroneDTO.getBatteryPercentage() != null) {
                existingDrone.setBatteryPercentage(updateDroneDTO.getBatteryPercentage());
            }
            if (updateDroneDTO.getWeight() != null) {
                existingDrone.setWeight(updateDroneDTO.getWeight());
            }
            if (updateDroneDTO.getModel() != null){
                existingDrone.setDroneModel(DroneModel.valueOf(updateDroneDTO.getModel()));
            }
            if (updateDroneDTO.getDroneState() != null) {
                existingDrone.setDroneState(DroneState.valueOf(updateDroneDTO.getDroneState()));
            }
            UpdateDroneDTO.UpdateDroneResponse response = UpdateDroneDTO.UpdateDroneResponse.builder().serialNumber(existingDrone.getSerialNumber()).build();


            Drone updatedDrone = droneJPAService.saveDrone(existingDrone);
            return ResponseEntity.ok(createSuccessResponse(response, "Drone updated successfully"));

        } catch (Exception e) {
            log.error("Error while updating drone with id: {}", droneId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse(e.getLocalizedMessage(), "Error while updating drone"));
        }
    }

}
