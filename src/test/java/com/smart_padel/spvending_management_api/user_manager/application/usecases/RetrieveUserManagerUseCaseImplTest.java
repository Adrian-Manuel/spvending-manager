package com.smart_padel.spvending_management_api.user_manager.application.usecases;

import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.out.UserManagerRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveUserManagerUseCaseImplTest {

    @Mock
    private UserManagerRepositoryPort userManagerRepositoryPort;

    @InjectMocks
    private RetrieveUserManagerUseCaseImpl retrieveUserManagerUseCase;

    @Test
    void getUserManagerById_ReturnsUserManager_WhenValidIdProvided() {
        UUID userManagerId = UUID.randomUUID();
        UserManager userManager = mock(UserManager.class);
        when(userManagerRepositoryPort.findById(userManagerId)).thenReturn(userManager);
        UserManager result = retrieveUserManagerUseCase.getUserManagerById(userManagerId);
        assertThat(result).isEqualTo(userManager);
        verify(userManagerRepositoryPort).findById(userManagerId);
    }

    @Test
    void getAllUserManager_ReturnsPageOfUserManagers_WhenValidInputsProvided() {
        String search = "test";
        Pageable pageable = mock(Pageable.class);
        Page<UserManager> userManagerPage = mock(Page.class);
        when(userManagerRepositoryPort.findAll(search, pageable)).thenReturn(userManagerPage);
        Page<UserManager> result = retrieveUserManagerUseCase.getAllUserManager(search, pageable);
        assertThat(result).isEqualTo(userManagerPage);
        verify(userManagerRepositoryPort).findAll(search, pageable);
    }


}