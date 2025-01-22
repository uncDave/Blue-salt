package com.blusalt.blusalt.service.JpaService;

import com.blusalt.blusalt.entity.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleJPAService {

    Optional<Role> findById(UUID id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
    Role saveRole(Role role);
    void save(List<Role> collection);

}
