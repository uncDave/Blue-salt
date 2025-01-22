package com.blusalt.blusalt.dto.auditTrail;

import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.entity.DroneAuditTrail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AuditTrail {

    @Getter
    @Setter
    public static class GetAuditTrail{

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
            private List<DroneAuditTrail> auditTrailList;
            private long  totalAuditTrail;
            private int page, totalPages;
        }
    }
}
