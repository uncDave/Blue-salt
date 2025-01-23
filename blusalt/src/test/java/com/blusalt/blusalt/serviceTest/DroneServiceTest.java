package com.blusalt.blusalt.serviceTest;

import com.blusalt.blusalt.dto.drone.LoadDroneDTO;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.enums.DroneState;
import com.blusalt.blusalt.service.AdminService;
import com.blusalt.blusalt.service.DroneAuditTrailService;
import com.blusalt.blusalt.service.DroneService;
import com.blusalt.blusalt.service.JpaService.DroneJPAService;
import com.blusalt.blusalt.service.JpaService.MedicationJPAService;
import com.blusalt.blusalt.service.impl.AdminServiceImpl;
import com.blusalt.blusalt.service.impl.DroneImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DroneServiceTest {
    @Mock
    private DroneJPAService droneJPAService;

    @Mock
    private MedicationJPAService medicationJPAService;

    @Mock
    private DroneAuditTrailService droneAuditTrailService;

    @InjectMocks
    private DroneImpl droneService;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Drone drone;
    private Medication medication;
    private LoadDroneDTO.Request loadRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test drone and medication
        drone = new Drone();
        drone.setId(UUID.randomUUID());
        drone.setSerialNumber("12345");
        drone.setWeight(10);
        drone.setBatteryPercentage(100.0);
        drone.setDroneState(DroneState.IDLE);

        medication = new Medication();
        medication.setId(UUID.randomUUID());
        medication.setCode("M001");
        medication.setWeight(3);

        loadRequest = new LoadDroneDTO.Request(drone.getId(), Arrays.asList(medication.getId()));;
    }

    @Test
    void loadDrone_ShouldLoadSuccessfully_WhenDroneAndMedicationExist() {

        when(droneJPAService.findById(drone.getId())).thenReturn(Optional.of(drone));
        when(medicationJPAService.findById(medication.getId())).thenReturn(Optional.of(medication));

        var response = adminService.loadDrone(loadRequest);

        // Assertions
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(drone.getMedications().contains(medication));
        assertEquals(DroneState.LOADED, drone.getDroneState());
        assertEquals(3, drone.getTotalWeightState());
        verify(droneJPAService, times(1)).saveDrone(drone);
    }

    @Test
    void loadDrone_ShouldReturnNotFound_WhenDroneDoesNotExist() {
        when(droneJPAService.findById(drone.getId())).thenReturn(Optional.empty());

        var response = adminService.loadDrone(loadRequest);

        assertEquals(409, response.getStatusCodeValue());
    }

    @Test
    void loadDrone_ShouldReturnNotFound_WhenMedicationDoesNotExist() {
        when(droneJPAService.findById(drone.getId())).thenReturn(Optional.of(drone));
        when(medicationJPAService.findById(medication.getId())).thenReturn(Optional.empty());


        var response = adminService.loadDrone(loadRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void loadDrone_ShouldReturnBadRequest_WhenDroneExceedsWeightCapacity() {

        drone.setTotalWeightState(8);
        medication.setWeight(5);

        when(droneJPAService.findById(drone.getId())).thenReturn(Optional.of(drone));
        when(medicationJPAService.findById(medication.getId())).thenReturn(Optional.of(medication));

        var response = adminService.loadDrone(loadRequest);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void loadDrone_ShouldReturnInternalServerError_WhenExceptionOccurs() {

        when(droneJPAService.findById(drone.getId())).thenThrow(new RuntimeException("Database error"));


        var response = adminService.loadDrone(loadRequest);


        assertEquals(500, response.getStatusCodeValue());
    }
}
