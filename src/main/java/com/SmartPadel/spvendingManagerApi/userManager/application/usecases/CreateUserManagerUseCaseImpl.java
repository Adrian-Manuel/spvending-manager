package com.SmartPadel.spvendingManagerApi.userManager.application.usecases;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.CreateUserManagerUseCase;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out.UserManagerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserManagerUseCaseImpl implements CreateUserManagerUseCase {

    private final UserManagerRepositoryPort userManagerRepositoryPort;

    @Override
    public UserManager createUserManager(UUID tenantId, UUID clubId,UserManager userManager) throws Exception {
        return userManagerRepositoryPort.save(tenantId,clubId, userManager);
    }
}
