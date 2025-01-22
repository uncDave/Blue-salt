package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.entity.Drone;
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
                        .body(createFailureResponse("",""));
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
}
