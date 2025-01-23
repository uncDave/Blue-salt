package com.blusalt.blusalt.dto.drone;

import com.blusalt.blusalt.entity.Medication;
import com.blusalt.blusalt.enums.DroneState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateDroneDTO {
    @Getter
    @Setter
    public static class UpdateDroneRequest {
        private String serialNumber;
        private Double batteryPercentage;
        private Double weight;
        private String droneState;
        private String model;
    }

    @Getter
    @Setter
    @Builder
    public static class UpdateDroneResponse {
        private String serialNumber;
    }
}
