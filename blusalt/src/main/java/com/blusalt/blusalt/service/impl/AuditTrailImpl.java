package com.blusalt.blusalt.service.impl;

import com.blusalt.blusalt.dto.auditTrail.AuditTrail;
import com.blusalt.blusalt.dto.drone.OnboardDroneDTO;
import com.blusalt.blusalt.entity.DroneAuditTrail;
import com.blusalt.blusalt.service.AuditTrailService;
import com.blusalt.blusalt.service.JpaService.DroneAuditTrailJPAService;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createFailureResponse;
import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createSuccessResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditTrailImpl implements AuditTrailService {

    private final DroneAuditTrailJPAService droneAuditTrailJPAService;
    @Override
    public ResponseEntity<ApiResponse<?>> getAll(AuditTrail.GetAuditTrail getAuditTrail) {
        try {
            log.info("Query: {}", getAuditTrail);
            Sort.Direction direction = getAuditTrail.getOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sortBy = Sort.by(direction, getAuditTrail.getSortBy());
            Pageable pageable = PageRequest.of(getAuditTrail.getPage(), getAuditTrail.getLimit(), sortBy);

            Page<DroneAuditTrail> all = droneAuditTrailJPAService.findAll(pageable);
            if (all.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(createSuccessResponse("", "no log"));
            }

            AuditTrail.GetAuditTrail.Response response = new AuditTrail.GetAuditTrail.Response();
            response.setAuditTrailList(all.getContent());
            response.setPage(all.getTotalPages());
            response.setTotalPages(all.getTotalPages());
            response.setTotalAuditTrail(all.getTotalElements());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(createSuccessResponse(response, "all drones retrieved"));

        }catch (IllegalArgumentException e) {
            log.warn("error while getting all logs");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(createFailureResponse(e.getLocalizedMessage(), e.getMessage()));
        }
    }
}
