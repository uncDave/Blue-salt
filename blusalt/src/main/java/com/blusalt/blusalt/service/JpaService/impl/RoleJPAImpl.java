package com.blusalt.blusalt.service.JpaService.impl;

import com.blusalt.blusalt.entity.Role;
import com.blusalt.blusalt.repository.RoleRepository;
import com.blusalt.blusalt.service.JpaService.RoleJPAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleJPAImpl implements RoleJPAService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
       return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void save(List<Role> collection) {
        roleRepository.saveAll(collection);
    }
}
