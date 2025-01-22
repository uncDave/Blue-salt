package com.blusalt.blusalt.service.JpaService;

import com.blusalt.blusalt.entity.BaseUser;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.enums.DroneState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DroneJPAService {
    Optional<Drone> findById(UUID id);
    Optional<Drone> findBySerialNumber(String serialNumber);
    Page<Drone> findByDroneState(DroneState droneState, Pageable pageable);
    Page<Drone> findAll(Pageable pageable);
    List<Drone> findAll();
    Drone saveDrone(Drone drone);
}
