package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.adapters;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out.UserManagerRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class UserManagerRepositoryAdapter implements UserManagerRepositoryPort {
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
