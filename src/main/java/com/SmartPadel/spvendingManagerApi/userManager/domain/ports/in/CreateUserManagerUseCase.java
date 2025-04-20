package com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;

import java.util.UUID;

public interface CreateUserManagerUseCase {
    UserManager createUserManager(UUID tenantId, UUID clubId,UserManager userManager);
}
