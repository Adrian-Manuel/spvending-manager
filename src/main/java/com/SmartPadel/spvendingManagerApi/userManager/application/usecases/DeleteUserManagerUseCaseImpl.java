package com.SmartPadel.spvendingManagerApi.userManager.application.usecases;

import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.DeleteUserManagerUseCase;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out.UserManagerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserManagerUseCaseImpl implements DeleteUserManagerUseCase {

    private final UserManagerRepositoryPort userManagerRepositoryPort;

    @Override
    public void deleteUserManager(UUID userManagerId) {
        userManagerRepositoryPort.delete(userManagerId);
    }
}
