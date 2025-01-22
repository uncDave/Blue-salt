package com.blusalt.blusalt.repository;

import com.blusalt.blusalt.entity.BaseUser;
import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.enums.DroneState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DroneRepository extends JpaRepository<Drone, UUID> {
    Optional<Drone> findDroneBySerialNumber(String serialNumber);
    Page<Drone> findByDroneState(DroneState droneState, Pageable pageable);
//    Optional<Drone> findDroneById(String id);


}
