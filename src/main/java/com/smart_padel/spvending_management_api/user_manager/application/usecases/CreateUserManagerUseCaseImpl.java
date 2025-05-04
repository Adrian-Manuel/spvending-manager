package com.smart_padel.spvending_management_api.user_manager.application.usecases;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.CreateUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.GeneralSecurityException;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CreateUserManagerUseCaseImpl implements CreateUserManagerUseCase {
    private final UserManagerRepositoryPort userManagerRepositoryPort;
    @Override
    public UserManager createUserManager(UUID tenantId, UUID clubId,UserManager userManager) throws GeneralSecurityException {
        return userManagerRepositoryPort.save(tenantId,clubId, userManager);
    }
}
