package com.blusalt.blusalt.service.JpaService;

import com.blusalt.blusalt.entity.BaseUser;

import java.util.Optional;

public interface BaseUserJPAService {
    Optional<BaseUser> findByEmail(String email);
    Optional<BaseUser> findById(String id);
    boolean existsBaseUserByEmail(String userName);
    BaseUser saveUser(BaseUser user);



}
