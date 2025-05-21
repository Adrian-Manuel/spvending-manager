package com.smart_padel.spvending_management_api.user_manager.application.usecases;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.UpdateUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class UpdateUserManagerUseCaseImpl implements UpdateUserManagerUseCase {
    private final UserManagerRepositoryPort userManagerRepositoryPort;
    @Override
    public UserManager updateUserManager(UUID userManagerId, UserManager userManager) {
        return userManagerRepositoryPort.update(userManagerId, userManager);
    }
}
