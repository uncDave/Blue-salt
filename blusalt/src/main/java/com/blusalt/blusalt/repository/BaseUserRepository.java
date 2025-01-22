package com.blusalt.blusalt.repository;

import com.blusalt.blusalt.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BaseUserRepository extends JpaRepository<BaseUser, UUID> {
    Optional<BaseUser> findBaseUserByEmail(String email);
    boolean existsBaseUserByEmail(String userName);
}
