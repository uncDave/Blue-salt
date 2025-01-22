package com.blusalt.blusalt.dto.medication;

import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.Medication;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateMedication {

    @Getter
    @Setter
    public static class Request{

        @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Name can only contain letters, numbers, '-', and '_'")
        private String name;

        private long weight;

        @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code can only contain uppercase letters, numbers, and '_'")
        private String code;

        private String logoUrl;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private UUID Id;
        private String logoUrl;
    }

    @Getter
    @Setter
    public static class GetMedication{

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
            private List<Medication> medication;
            private long  totalDrones;
            private int page, totalPages;
        }
    }
}
