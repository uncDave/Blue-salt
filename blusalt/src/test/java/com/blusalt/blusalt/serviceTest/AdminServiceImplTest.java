package com.blusalt.blusalt.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.enums.DroneModel;
import com.blusalt.blusalt.enums.DroneState;
import com.blusalt.blusalt.service.JpaService.MedicationJPAService;
import com.blusalt.blusalt.service.impl.AdminServiceImpl;
import com.blusalt.blusalt.service.JpaService.DroneJPAService;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

class AdminServiceImplTest {

    @Mock
    private DroneJPAService droneJPAService;

    @Mock
    private MedicationJPAService medicationJPAService;

    @InjectMocks
    private AdminServiceImpl adminServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void onboardDrone_ShouldReturnSuccess_WhenDroneIsCreated() {
        OnboardDroneDTO.Request request = OnboardDroneDTO.Request.builder()
                .serialNumber("SN123M")
                .model(DroneModel.MIDDLEWEIGHT.name())
                .weight(80.0)
                .batteryCapacity(5.0)
                .build();


        Drone savedDrone = new Drone();
        savedDrone.setId(UUID.fromString("a3d6665f-058f-42e1-bed1-85f44a85d42d"));
        savedDrone.setSerialNumber("SN123M");
        savedDrone.setDroneModel(DroneModel.MIDDLEWEIGHT);
        savedDrone.setDroneState(DroneState.IDLE);


        when(droneJPAService.findBySerialNumber("SN123M")).thenReturn(Optional.empty());
        when(droneJPAService.saveDrone(any(Drone.class))).thenReturn(savedDrone);

        ResponseEntity<ApiResponse<?>> response = adminServiceImpl.onboardDrone(request);


        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertFalse(response.getBody().isStatus());
        assertEquals("drone created", response.getBody().getMessage());
    }


    @Test
    void onboardDrone_ShouldReturnConflict_WhenDroneAlreadyExists() {
        OnboardDroneDTO.Request request = OnboardDroneDTO.Request.builder()
                .serialNumber("SN123M")
                .model("M1")
                .weight(80.0)
                .batteryCapacity(5.0)
                .build();

        Drone existingDrone = new Drone();
        existingDrone.setSerialNumber("SN123M");

        when(droneJPAService.findBySerialNumber("SN123M")).thenReturn(Optional.of(existingDrone));

        ResponseEntity<ApiResponse<?>> response = adminServiceImpl.onboardDrone(request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.getBody().isStatus());
        assertEquals("drone exists", response.getBody().getMessage());
    }

    @Test
    void createMedication_ShouldReturnSuccess_WhenMedicationIsCreated() {
        CreateMedication.Request request = CreateMedication.Request.builder()
                .code("MED123")
                .weight(10)
                .name("Medication A")
                .logoUrl("http://example.com/logo.png")
                .build();

        Medication newMedication = Medication.builder()
                .code("MED123")
                .name("Medication A")
                .weight(10.0)
                .logoUrl("http://example.com/logo.png")
                .build();

        when(medicationJPAService.findByCode("MED123")).thenReturn(Optional.empty());
        when(medicationJPAService.saveMedication(any(Medication.class))).thenReturn(newMedication);

        ResponseEntity<ApiResponse<?>> response = adminServiceImpl.CreateMedication(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("medication created", response.getBody().getMessage());

        CreateMedication.Response responseBody = (CreateMedication.Response) response.getBody().getData();
        assertNotNull(responseBody);
        assertEquals(newMedication.getId(), responseBody.getId());
        assertEquals(newMedication.getLogoUrl(), responseBody.getLogoUrl());
    }




    @Test
    void createMedication_ShouldReturnConflict_WhenMedicationAlreadyExists() {

        CreateMedication.Request request = CreateMedication.Request.builder()
                .code("MED123")
                .weight(10)
                .name("Medication A")
                .logoUrl("http://example.com/logo.png")
                .build();

        Medication existingMedication = Medication.builder()
                .code("MED123")
                .name("Medication A")
                .weight(10.0)
                .logoUrl("http://example.com/logo.png")
                .build();

        when(medicationJPAService.findByCode("MED123")).thenReturn(Optional.of(existingMedication));


        ResponseEntity<ApiResponse<?>> response = adminServiceImpl.CreateMedication(request);


        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("medication with code exists already", response.getBody().getMessage());
    }


}
