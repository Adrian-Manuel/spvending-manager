package com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in;

import java.util.UUID;

public interface DeleteUserManagerUseCase {
    void deleteUserManager(UUID userManagerId);
}
