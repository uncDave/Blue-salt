package com.blusalt.blusalt.service;

import com.blusalt.blusalt.dto.drone.LoadDroneDTO;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AdminService {

    ResponseEntity<ApiResponse<?>> onboardDrone(OnboardDroneDTO.Request request);
    ResponseEntity<ApiResponse<?>> CreateMedication(CreateMedication.Request request);
    ResponseEntity<ApiResponse<?>> loadDrone(LoadDroneDTO.Request request);
    ResponseEntity<ApiResponse<?>> makeDroneAvailableForLoading(UUID droneId);
    ResponseEntity<ApiResponse<?>> getItemsLoadedOnDrone(UUID id);

}
