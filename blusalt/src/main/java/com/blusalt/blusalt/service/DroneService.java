package com.blusalt.blusalt.service;

import com.blusalt.blusalt.dto.auth.LoginDTO;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.drone.UpdateDroneDTO;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface DroneService {
    ResponseEntity<ApiResponse<?>> getAll(OnboardDroneDTO.GetDrone getDrone);
    ResponseEntity<ApiResponse<?>> getDroneById(UUID id);
    ResponseEntity<ApiResponse<?>> getDroneBatteryLevel(UUID id);
    ResponseEntity<ApiResponse<?>> dronesAvailableForLoading(OnboardDroneDTO.GetDrone getDrone);
    ResponseEntity<ApiResponse<?>> getDroneBySerialNumber(String serialNumber);
    ResponseEntity<ApiResponse<?>> updateDrone(UUID droneId, UpdateDroneDTO.UpdateDroneRequest updateDroneDTO);


}
