
package com.smart_padel.spvending_management_api.user_manager.infrastructure.utils;

import com.smart_padel.spvending_management_api.shared.exceptions.ParamRequiredException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.repository.JpaUserManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagerHelperAdapterTest {

    @Mock
    private JpaUserManagerRepository repo;

    @Test
    void validateClubOrTenant_ThrowsParamRequiredException_OnInvalidCases() {
        UUID tenantId = UUID.randomUUID();
        UUID clubId = UUID.randomUUID();

        Object[][] cases = {
                {null, null, "1", "some club id or tenant id is required"},
                {tenantId, clubId, "1", "only one of the two parameters is needed"},
                {tenantId, null, "1", "The user is type 1, the club id is required."},
                {null, clubId, "2", "The user is type 2, the tenant id is required."}
        };

        for (Object[] c : cases) {
            assertThatThrownBy(() -> UserManagerHelperAdapter.validateClubOrTenant(
                    (UUID) c[0], (UUID) c[1], (String) c[2]))
                    .isInstanceOf(ParamRequiredException.class)
                    .hasMessage((String) c[3]);
        }
    }

    @Test
    void validateClubOrTenant_DoesNotThrow_OnValidCases() {
        assertThatCode(() -> UserManagerHelperAdapter.validateClubOrTenant(null, UUID.randomUUID(), "1"))
                .doesNotThrowAnyException();
        assertThatCode(() -> UserManagerHelperAdapter.validateClubOrTenant(UUID.randomUUID(), null, "2"))
                .doesNotThrowAnyException();
        assertThatCode(() -> UserManagerHelperAdapter.validateClubOrTenant(UUID.randomUUID(), null, "3"))
                .doesNotThrowAnyException();
        assertThatCode(() -> UserManagerHelperAdapter.validateClubOrTenant(null, UUID.randomUUID(), "3"))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUserUniqueness_ThrowsException_OnDuplicates() {
        UserManager user = mock(UserManager.class);

        when(user.getUserName()).thenReturn("user");
        when(user.getMicronId()).thenReturn("mid");
        when(user.getMicronUser()).thenReturn("muser");

        when(repo.existsByUserName("user")).thenReturn(true);
        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserUniqueness(repo, user))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("a user with that name already exists");

        when(repo.existsByUserName("user")).thenReturn(false);
        when(repo.existsByMicronId("mid")).thenReturn(true);
        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserUniqueness(repo, user))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("a user with that micron Id already exists");

        when(repo.existsByMicronId("mid")).thenReturn(false);
        when(repo.existsByMicronUser("muser")).thenReturn(true);
        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserUniqueness(repo, user))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("a user with that micron user already exists");
    }

    @Test
    void validateUserUniqueness_DoesNotThrow_WhenNoDuplicates() {
        UserManager user = mock(UserManager.class);
        when(user.getUserName()).thenReturn(null);
        when(user.getMicronId()).thenReturn(null);
        when(user.getMicronUser()).thenReturn(null);

        when(repo.existsByUserName(null)).thenReturn(false);
        when(repo.existsByMicronId(null)).thenReturn(false);
        when(repo.existsByMicronUser(null)).thenReturn(false);

        assertThatCode(() -> UserManagerHelperAdapter.validateUserUniqueness(repo, user))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUserManagerExists_ThrowsOrNotDependingOnExistence() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(false);
        when(repo.existsById(null)).thenReturn(false);

        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserManagerExists(repo, id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("The userManager does not exist");
        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserManagerExists(repo, null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("The userManager does not exist");

        when(repo.existsById(id)).thenReturn(true);
        assertThatCode(() -> UserManagerHelperAdapter.validateUserManagerExists(repo, id))
                .doesNotThrowAnyException();
    }

    @Test
    void constructor_ThrowsException() {
        assertThatThrownBy(UserManagerHelperAdapter::new)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Util class");
    }
}