package com.blusalt.blusalt.dto.medication;

import com.blusalt.blusalt.enums.DroneState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMedicationDTO {
    @Getter
    @Setter
    public static class UpdateMedicationRequest {
        private String name;
        private Double weight;
        private String code;
        private String logoUrl;
    }

    @Getter
    @Setter
    @Builder
    public static class UpdateDroneResponse {
        private String code;
    }
}
