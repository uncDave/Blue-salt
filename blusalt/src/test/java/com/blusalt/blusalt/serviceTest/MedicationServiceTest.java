package com.blusalt.blusalt.serviceTest;

import com.blusalt.blusalt.dto.medication.CreateMedication;
import com.blusalt.blusalt.dto.medication.UpdateMedicationDTO;
import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.service.JpaService.MedicationJPAService;
import com.blusalt.blusalt.service.impl.MedicationImpl;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {
    @InjectMocks
    private MedicationImpl medicationService;

    @Mock
    private MedicationJPAService medicationJPAService;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private MultipartFile multipartFile;

    private Medication mockMedication;

    @BeforeEach
    public void setUp() {
        mockMedication = new Medication();
        mockMedication.setId(UUID.randomUUID());
        mockMedication.setName("Paracetamol");
        mockMedication.setWeight(500);
        mockMedication.setCode("P12345");
    }

    @Test
    public void testGetAll_MedicationsFound() {
        CreateMedication.GetMedication getMedication = new CreateMedication.GetMedication();
        getMedication.setPage(0);
        getMedication.setLimit(10);
        getMedication.setSortBy("name");
        getMedication.setOrder("asc");

        when(medicationJPAService.findAll(any())).thenReturn(mockPageOfMedications());

        ResponseEntity<ApiResponse<?>> response = medicationService.getAll(getMedication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("all medications retrieved");
    }

    @Test
    public void testGetMedicationById_MedicationFound() {
        UUID medicationId = mockMedication.getId();

        when(medicationJPAService.findById(medicationId)).thenReturn(Optional.of(mockMedication));

        ResponseEntity<ApiResponse<?>> response = medicationService.getMedicationById(medicationId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("medication retrieved");
    }

    @Test
    public void testGetMedicationById_MedicationNotFound() {
        UUID medicationId = UUID.randomUUID();

        when(medicationJPAService.findById(medicationId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = medicationService.getMedicationById(medicationId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_GATEWAY);

        assertThat(response.getBody().getMessage()).isEqualTo("medication not found");

        assertThat(response.getBody().isStatus()).isFalse();

        assertThat(response.getBody().getError()).isEqualTo("");
    }

    @Test
    public void testUploadMedicationImage_Success() throws IOException {

        String mockUrl = "http://cloudinary.com/medication_image";
        Map<String, String> uploadResponse = Map.of("secure_url", mockUrl);

        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.upload(any(byte[].class), any())).thenReturn(uploadResponse);

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn("someImageData".getBytes());

        ResponseEntity<ApiResponse<?>> response = medicationService.uploadMedicationImage(multipartFile);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Logo Successfully uploaded");
    }

    @Test
    public void testUploadMedicationImage_Failure() throws IOException {

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), any())).thenThrow(new IOException("Upload failed"));

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn("someImageData".getBytes());

        ResponseEntity<ApiResponse<?>> response = medicationService.uploadMedicationImage(multipartFile);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("invalid request");
    }

    @Test
    public void testUpdateMedication_Success() {
        UUID medicationId = mockMedication.getId();
        UpdateMedicationDTO.UpdateMedicationRequest request = new UpdateMedicationDTO.UpdateMedicationRequest();
        request.setName("Updated Paracetamol");

        when(medicationJPAService.findById(medicationId)).thenReturn(Optional.of(mockMedication));
        when(medicationJPAService.saveMedication(any(Medication.class))).thenReturn(mockMedication);

        ResponseEntity<ApiResponse<?>> response = medicationService.updateMedication(medicationId, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("medication updated successfully");
    }

    @Test
    public void testUpdateMedication_MedicationNotFound() {
        UUID medicationId = UUID.randomUUID();
        UpdateMedicationDTO.UpdateMedicationRequest request = new UpdateMedicationDTO.UpdateMedicationRequest();
        request.setName("Updated Paracetamol");


        when(medicationJPAService.findById(medicationId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = medicationService.updateMedication(medicationId, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_GATEWAY);
        assertThat(response.getBody().getMessage()).isEqualTo("medication not found");
    }


    private Page<Medication> mockPageOfMedications() {
        return new PageImpl<>(List.of(mockMedication), PageRequest.of(0, 10), 1);
    }
}
