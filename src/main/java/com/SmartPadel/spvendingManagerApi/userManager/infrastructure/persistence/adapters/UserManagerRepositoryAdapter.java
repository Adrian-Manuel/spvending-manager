package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.adapters;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out.UserManagerRepositoryPort;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.repository.JpaUserManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Transactional
@Component
@RequiredArgsConstructor
public class UserManagerRepositoryAdapter implements UserManagerRepositoryPort {

    private final JpaUserManagerRepository jpaUserManagerRepository;

    @Override
    public UserManager save(UUID tenantId, UUID clubId, UserManager userManager) {

        return null;
    }

    @Override
    public UserManager update(UUID tenantId, UUID clubId, UUID userManagerId, UserManager userManager) {
        return null;
    }

    @Override
    public void delete(UUID userManagerId) {

    }

    @Override
    public Page<UserManager> findAll(String search, Pageable pageable) {
        return null;
    }

    @Override
    public UserManager findById(UUID userManager) {
        return null;
    }
}
