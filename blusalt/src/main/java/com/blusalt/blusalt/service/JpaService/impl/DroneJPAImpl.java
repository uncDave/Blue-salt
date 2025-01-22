package com.blusalt.blusalt.service.JpaService.impl;

import com.blusalt.blusalt.entity.Drone;
import com.blusalt.blusalt.enums.DroneState;
import com.blusalt.blusalt.repository.DroneRepository;
import com.blusalt.blusalt.service.JpaService.DroneJPAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DroneJPAImpl implements DroneJPAService {
    private final DroneRepository droneRepository;

    @Override
    public Optional<Drone> findById(UUID id) {
        return droneRepository.findById(id);
    }

    @Override
    public Optional<Drone> findBySerialNumber(String serialNumber) {
        return droneRepository.findDroneBySerialNumber(serialNumber);
    }

    @Override
    public Page<Drone> findByDroneState(DroneState droneState, Pageable pageable) {
        return droneRepository.findByDroneState(droneState,pageable);
    }

    @Override
    public Page<Drone> findAll(Pageable pageable) {
        return droneRepository.findAll(pageable);
    }

    @Override
    public List<Drone> findAll() {
        return droneRepository.findAll();
    }

    @Override
    public Drone saveDrone(Drone drone) {
       return droneRepository.save(drone);
    }
}
