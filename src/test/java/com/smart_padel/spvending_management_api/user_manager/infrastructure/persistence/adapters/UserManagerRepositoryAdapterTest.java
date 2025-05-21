package com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.adapters;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.repository.JpaClubRepository;
import com.smart_padel.spvending_management_api.club.infrastructure.utils.ClubHelperAdapter;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.shared.utils.PersistenceUtils;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.smart_padel.spvending_management_api.tenant.infrastructure.utils.TenantHelperAdapter;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity.UserManagerEntity;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.repository.JpaUserManagerRepository;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.utils.UserManagerHelperAdapter;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.utils.UserManagerSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagerRepositoryAdapterTest {

    @Mock private JpaUserManagerRepository jpaUserManagerRepository;
    @Mock private JpaClubRepository jpaClubRepository;
    @Mock private JpaTenantRepository jpaTenantRepository;
    @InjectMocks private UserManagerRepositoryAdapter adapter;

    private UUID tenantId, clubId, userManagerId;
    private UserManager userManager;
    private UserManagerEntity userManagerEntity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        tenantId = UUID.randomUUID();
        clubId = UUID.randomUUID();
        userManagerId = UUID.randomUUID();
        userManager = mock(UserManager.class);
        userManagerEntity = mock(UserManagerEntity.class);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void save_ReturnsUserManager_ForClubOrTenant() throws GeneralSecurityException {
        ClubEntity clubEntity = mock(ClubEntity.class);
        TenantEntity tenantEntity = mock(TenantEntity.class);
        UserManagerEntity savedEntity = mock(UserManagerEntity.class);
        UserManager expected = mock(UserManager.class);

        try (MockedStatic<UserManagerHelperAdapter> helper = mockStatic(UserManagerHelperAdapter.class);
             MockedStatic<UserManagerEntity> entityStatic = mockStatic(UserManagerEntity.class);
             MockedStatic<ClubHelperAdapter> clubHelper = mockStatic(ClubHelperAdapter.class);
             MockedStatic<TenantHelperAdapter> tenantHelper = mockStatic(TenantHelperAdapter.class)) {

            entityStatic.when(() -> UserManagerEntity.fromDomainModel(userManager)).thenReturn(userManagerEntity);
            when(jpaUserManagerRepository.save(userManagerEntity)).thenReturn(savedEntity);
            when(savedEntity.toDomainModel()).thenReturn(expected);

            clubHelper.when(() -> ClubHelperAdapter.getClubOrThrow(jpaClubRepository, clubId)).thenReturn(clubEntity);
            assertEquals(expected, adapter.save(null, clubId, userManager));
            verify(jpaUserManagerRepository).save(userManagerEntity);

            tenantHelper.when(() -> TenantHelperAdapter.getTenantOrThrow(jpaTenantRepository, tenantId)).thenReturn(tenantEntity);
            assertEquals(expected, adapter.save(tenantId, null, userManager));
            verify(jpaUserManagerRepository, times(2)).save(userManagerEntity);
        }
    }

    @Test
    void update_ReturnsUserManager_ForClubOrTenant() {
        ClubEntity clubEntity = mock(ClubEntity.class);
        TenantEntity tenantEntity = mock(TenantEntity.class);
        UserManagerEntity updatedEntity = mock(UserManagerEntity.class);
        UserManager expected = mock(UserManager.class);

        try (MockedStatic<UserManagerHelperAdapter> helper = mockStatic(UserManagerHelperAdapter.class);
             MockedStatic<UserManagerEntity> entityStatic = mockStatic(UserManagerEntity.class);
             MockedStatic<ClubHelperAdapter> clubHelper = mockStatic(ClubHelperAdapter.class);
             MockedStatic<TenantHelperAdapter> tenantHelper = mockStatic(TenantHelperAdapter.class)) {

            entityStatic.when(() -> UserManagerEntity.fromDomainModel(userManager)).thenReturn(userManagerEntity);
            when(jpaUserManagerRepository.save(userManagerEntity)).thenReturn(updatedEntity);
            when(updatedEntity.toDomainModel()).thenReturn(expected);

            clubHelper.when(() -> ClubHelperAdapter.getClubOrThrow(jpaClubRepository, clubId)).thenReturn(clubEntity);
            assertEquals(expected, adapter.update(userManagerId, userManager));

            tenantHelper.when(() -> TenantHelperAdapter.getTenantOrThrow(jpaTenantRepository, tenantId)).thenReturn(tenantEntity);
            assertEquals(expected, adapter.update(userManagerId, userManager));
        }
    }

    @Test
    void delete_DeletesUserManager() {
        try (MockedStatic<UserManagerHelperAdapter> helper = mockStatic(UserManagerHelperAdapter.class)) {
            doNothing().when(jpaUserManagerRepository).deleteById(userManagerId);
            adapter.delete(userManagerId);
            verify(jpaUserManagerRepository).deleteById(userManagerId);
        }
    }

    @Test
    void findAll_ReturnsPageOfUserManagers() {
        Page<UserManagerEntity> entityPage = new PageImpl<>(Collections.singletonList(userManagerEntity), pageable, 1);
        Page<UserManager> expectedPage = new PageImpl<>(Collections.singletonList(userManager), pageable, 1);

        try (MockedStatic<UserManagerSpecification> specStatic = mockStatic(UserManagerSpecification.class);
             MockedStatic<PersistenceUtils> persistenceUtils = mockStatic(PersistenceUtils.class)) {

            Specification<UserManagerEntity> spec = mock(Specification.class);
            specStatic.when(() -> UserManagerSpecification.withFilter("search")).thenReturn(spec);
            when(jpaUserManagerRepository.findAll(spec, pageable)).thenReturn(entityPage);
            persistenceUtils.when(() -> PersistenceUtils.mapPageOrThrow(any(Page.class), eq("User managers not found"), any())).thenReturn(expectedPage);

            assertEquals(expectedPage, adapter.findAll("search", pageable));
            verify(jpaUserManagerRepository).findAll(spec, pageable);
        }
    }

    @Test
    void findById_ReturnsUserManager_OrThrows() {
        when(jpaUserManagerRepository.findById(userManagerId)).thenReturn(Optional.of(userManagerEntity));
        when(userManagerEntity.toDomainModel()).thenReturn(userManager);
        assertEquals(userManager, adapter.findById(userManagerId));

        when(jpaUserManagerRepository.findById(userManagerId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> adapter.findById(userManagerId));
    }

    @Test
    void validateClubOrTenant_DoesNotThrow_WhenUserTypeIsOtherThan1Or2() {
        UUID tid = UUID.randomUUID(), cid = UUID.randomUUID();
        assertThatCode(() -> UserManagerHelperAdapter.validateClubOrTenant(tid, null, "3")).doesNotThrowAnyException();
        assertThatCode(() -> UserManagerHelperAdapter.validateClubOrTenant(null, cid, "3")).doesNotThrowAnyException();
    }

    @Test
    void validateUserUniqueness_ThrowsException_WhenMicronIdOrUserExists() {
        UserManager user = mock(UserManager.class);
        when(user.getUserName()).thenReturn("uniqueUser");
        when(user.getMicronId()).thenReturn("existingMicronId");
        when(jpaUserManagerRepository.existsByUserName("uniqueUser")).thenReturn(false);
        when(jpaUserManagerRepository.existsByMicronId("existingMicronId")).thenReturn(true);

        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserUniqueness(jpaUserManagerRepository, user))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("a user with that micron Id already exists");

        when(user.getMicronId()).thenReturn("uniqueMicronId");
        when(user.getMicronUser()).thenReturn("existingMicronUser");
        when(jpaUserManagerRepository.existsByMicronId("uniqueMicronId")).thenReturn(false);
        when(jpaUserManagerRepository.existsByMicronUser("existingMicronUser")).thenReturn(true);

        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserUniqueness(jpaUserManagerRepository, user))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("a user with that micron user already exists");
    }

    @Test
    void validateUserUniqueness_DoesNotThrow_WhenAllFieldsNullAndNoDuplicates() {
        UserManager user = mock(UserManager.class);
        when(user.getUserName()).thenReturn(null);
        when(user.getMicronId()).thenReturn(null);
        when(user.getMicronUser()).thenReturn(null);
        when(jpaUserManagerRepository.existsByUserName(null)).thenReturn(false);
        when(jpaUserManagerRepository.existsByMicronId(null)).thenReturn(false);
        when(jpaUserManagerRepository.existsByMicronUser(null)).thenReturn(false);

        assertThatCode(() -> UserManagerHelperAdapter.validateUserUniqueness(jpaUserManagerRepository, user))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUserManagerExists_ThrowsException_WhenIdIsNull() {
        when(jpaUserManagerRepository.existsById(null)).thenReturn(false);
        assertThatThrownBy(() -> UserManagerHelperAdapter.validateUserManagerExists(jpaUserManagerRepository, null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("The userManager does not exist");
    }
}