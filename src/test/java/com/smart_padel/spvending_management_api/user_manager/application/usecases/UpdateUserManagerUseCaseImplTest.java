package com.smart_padel.spvending_management_api.user_manager.application.usecases;

import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserManagerUseCaseImplTest {

    @Mock
    private UserManagerRepositoryPort userManagerRepositoryPort;

    @InjectMocks
    private UpdateUserManagerUseCaseImpl updateUserManagerUseCase;

    @Test
    void updateUserManager_ReturnsUpdatedUserManager_WhenValidInputsProvided() {

        UUID userManagerId = UUID.randomUUID();
        UserManager userManager = mock(UserManager.class);
        UserManager updatedUserManager = mock(UserManager.class);

        when(userManagerRepositoryPort.update(userManagerId, userManager)).thenReturn(updatedUserManager);

        UserManager result = updateUserManagerUseCase.updateUserManager(userManagerId, userManager);

        assertThat(result).isEqualTo(updatedUserManager);
        verify(userManagerRepositoryPort).update(userManagerId, userManager);
    }

}
