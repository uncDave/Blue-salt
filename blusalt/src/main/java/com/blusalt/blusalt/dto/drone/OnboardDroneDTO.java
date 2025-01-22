package com.blusalt.blusalt.dto.drone;

import com.blusalt.blusalt.entity.Drone;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OnboardDroneDTO {


    @Getter
    @Setter
    public static class Request{
        @NotBlank
        @Size(max = 100)
        private String serialNumber;

        private String model;

        @NotNull
        @Min(0)
        @Max(500)
        private double weight;

        @NotNull
        @Min(0)
        @Max(100)
        private double batteryCapacity;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private UUID id;
        private String serialNumber;
    }

    @Getter
    @Setter
    public static class GetDrone{

        private int page = 0;

        private int limit = 10;

        private String order = "ASC";

        private String sortBy = "createdAt";

        @Override
        public String toString(){
            return "page: "+page+ ", limit: "+ limit+", order: "+order +", sort: "+sortBy;
        }

        @Getter
        @Setter
        public static class Response{
            private List<Drone> drones;
            private long  totalDrones;
            private int page, totalPages;
        }
    }
}
