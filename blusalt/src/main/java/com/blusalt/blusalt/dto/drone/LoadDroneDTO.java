package com.blusalt.blusalt.dto.drone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

public class LoadDroneDTO {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{
        private UUID droneId;
        private List<UUID> medicationId;

    }
}
