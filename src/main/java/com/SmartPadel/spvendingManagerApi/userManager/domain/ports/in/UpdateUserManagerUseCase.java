package com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;

import java.util.UUID;

public interface UpdateUserManagerUseCase {
    UserManager updateUserManager(UUID tenantId, UUID clubId, UUID userManagerId,UserManager userManager);
}
