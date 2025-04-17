package com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserManagerRepositoryPort {
    UserManager save (UUID tenantId, UUID clubId, UserManager userManager);
    UserManager update(UUID tenantId, UUID clubId, UUID userManagerId, UserManager userManager);
    void delete(UUID userManagerId);
    Page<UserManager> findAll(String search, Pageable pageable);
    UserManager findById(UUID userManager);
}
