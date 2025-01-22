package com.blusalt.blusalt.service.JpaService.impl;

import com.blusalt.blusalt.entity.BaseUser;
import com.blusalt.blusalt.repository.BaseUserRepository;
import com.blusalt.blusalt.service.JpaService.BaseUserJPAService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BaseUserJPAJPAImpl implements BaseUserJPAService {
    private final BaseUserRepository baseUserRepository;

    @Override
    @Transactional
    public Optional<BaseUser> findByEmail(String email) {
        return baseUserRepository.findBaseUserByEmail(email);
    }

    @Override
    public Optional<BaseUser> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsBaseUserByEmail(String userName) {
        return false;
    }

    @Override
    public BaseUser saveUser(BaseUser user) {
        return baseUserRepository.save(user);

    }
}
