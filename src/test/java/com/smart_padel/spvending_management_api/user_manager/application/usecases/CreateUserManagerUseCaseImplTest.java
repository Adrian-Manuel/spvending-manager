package com.smart_padel.spvending_management_api.user_manager.application.usecases;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.security.GeneralSecurityException;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserManagerUseCaseImplTest {

    @Mock
    private UserManagerRepositoryPort repo;

    @InjectMocks
    private CreateUserManagerUseCaseImpl useCase;

    @Test
    void createUserManager_ReturnsSavedUserManager_WhenValidInputs() throws GeneralSecurityException {
        UUID tenantId = UUID.randomUUID();
        UUID clubId = UUID.randomUUID();
        UserManager user = mock(UserManager.class);
        UserManager saved = mock(UserManager.class);
        when(repo.save(tenantId, clubId, user)).thenReturn(saved);
        assertThat(useCase.createUserManager(tenantId, clubId, user)).isEqualTo(saved);
        verify(repo).save(tenantId, clubId, user);
    }

    @Test
    void createUserManager_ThrowsGeneralSecurityException_WhenRepositoryFails() throws GeneralSecurityException {
        UUID tenantId = UUID.randomUUID();
        UUID clubId = UUID.randomUUID();
        UserManager user = mock(UserManager.class);
        when(repo.save(tenantId, clubId, user)).thenThrow(new GeneralSecurityException("Security error"));
        assertThatThrownBy(() -> useCase.createUserManager(tenantId, clubId, user))
                .isInstanceOf(GeneralSecurityException.class)
                .hasMessage("Security error");
        verify(repo).save(tenantId, clubId, user);
    }
}