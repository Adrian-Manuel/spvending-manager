package com.SmartPadel.spvendingManagerApi.userManager.application.usecases;
import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.UpdateUserManagerUseCase;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out.UserManagerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class UpdateUserManagerUseCaseImpl implements UpdateUserManagerUseCase {
    private final UserManagerRepositoryPort userManagerRepositoryPort;
    @Override
    public UserManager updateUserManager(UUID tenantId, UUID clubId, UUID userManagerId, UserManager userManager) {
        return userManagerRepositoryPort.update(tenantId, clubId, userManagerId, userManager);
    }
}
