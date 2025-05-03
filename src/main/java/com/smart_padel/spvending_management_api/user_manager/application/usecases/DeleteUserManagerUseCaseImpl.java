package com.smart_padel.spvending_management_api.user_manager.application.usecases;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.DeleteUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
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
