package com.smart_padel.spvending_management_api.user_manager.application.usecases;

import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserManagerUseCaseImplTest {

    @Mock
    private UserManagerRepositoryPort userManagerRepositoryPort;

    @InjectMocks
    private DeleteUserManagerUseCaseImpl deleteUserManagerUseCase;

    @Test
    void deleteUserManager_DeletesUserManager_WhenValidIdProvided() {
        UUID userManagerId = UUID.randomUUID();
        doNothing().when(userManagerRepositoryPort).delete(userManagerId);
        deleteUserManagerUseCase.deleteUserManager(userManagerId);
        verify(userManagerRepositoryPort).delete(userManagerId);
    }
}
