package com.smart_padel.spvending_management_api.club.infrastructure.persistence.adapters;

import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.adapters.ClubRepositoryAdapter;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.repository.JpaClubRepository;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClubRepositoryAdapterTest {

    @Mock
    private JpaClubRepository jpaClubRepository;
    @Mock
    private JpaTenantRepository jpaTenantRepository;

    @InjectMocks
    private ClubRepositoryAdapter clubRepositoryAdapter;

    private UUID tenantId;
    private UUID clubId;
    private Tenant tenant;
    private Club club;
    private ClubEntity clubEntity;
    private TenantEntity tenantEntity;
    private Pageable pageable;

    @BeforeEach
    void setup() {
        tenantId = UUID.randomUUID();
        clubId = UUID.randomUUID();

        tenant = Tenant.builder()
                .tenantId(tenantId)
                .name("Test Tenant")
                .cif("TESTCIF")
                .address("Test Address")
                .phone("123456789")
                .email("test@example.com")
                .remark("Test Remark")
                .micronId("TESTMICRONID")
                .clubsCount(1)
                .managers(Collections.emptyList())
                .build();

       club = Club.builder()
                .clubId(clubId)
                .name("Club Name")
                .cif("123235")
                .address("salmo")
                .phone("604085715")
                .email("dasilvaadrian@gmail.com")
                .remark("asdsad")
                .accountingId("123asdasd45465")
                .micronId("asda123232544sdfsd")
                .accountingId("ACC-123")
                .tenantName("Test Tenant")
                .machinesCount(0)
                .managers(Collections.emptyList())
                .build();

        tenantEntity = TenantEntity.fromDomainModel(tenant);

        clubEntity = ClubEntity.fromDomainModel(club);
        clubEntity.setTenantEntity(tenantEntity);
        pageable=PageRequest.of(0, 10);

    }

    @Test
    void findAll_shouldReturnPageOfClubs() {
        Page<ClubEntity> clubEntityPage = new PageImpl<>(List.of(clubEntity));
        doReturn(clubEntityPage).when(jpaClubRepository).findAll(any(Specification.class), eq(pageable));
        Page<Club> result = clubRepositoryAdapter.findAll("name:Club", pageable);
        assertThat(result.getContent()).hasSize(1);
        verify(jpaClubRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAllClubsSummary_shouldReturnListOfClubs() {
        when(jpaClubRepository.findAll()).thenReturn(List.of(clubEntity));
        List<Club> result = clubRepositoryAdapter.findAllClubsSummary();
        assertThat(result).hasSize(1);
        verify(jpaClubRepository).findAll();
    }

    @Test
    void findAllClubsByTenantId_shouldReturnPageOfClubs() {
        Page<ClubEntity> clubEntityPage = new PageImpl<>(List.of(clubEntity));
        when(jpaTenantRepository.existsById(tenantId)).thenReturn(true);
        when(jpaClubRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(clubEntityPage);
        Page<Club> result = clubRepositoryAdapter.findAllClubsByTenantId("name:Club", tenantId, pageable);
        assertThat(result.getContent()).hasSize(1);
        verify(jpaClubRepository).findAll(any(Specification.class), eq(pageable));
        verify(jpaTenantRepository).existsById(tenantId);
    }

    @Test
    void findAllClubsByTenantId_shouldThrowException_whenTenantDoesNotExist() {
        doThrow(new ResourceNotFoundException("Tenant not found")).when(jpaTenantRepository).existsById(tenantId);

        assertThatThrownBy(() -> clubRepositoryAdapter.findAllClubsByTenantId("name:Club", tenantId, pageable))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tenant not found");

        verify(jpaTenantRepository).existsById(tenantId);
        verify(jpaClubRepository, never()).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAllClubsByTenantId_shouldThrowException_whenNoClubsFound() {
        Page<ClubEntity> emptyPage = new PageImpl<>(Collections.emptyList());
        when(jpaTenantRepository.existsById(tenantId)).thenReturn(true);
        when(jpaClubRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(emptyPage);

        assertThatThrownBy(() -> clubRepositoryAdapter.findAllClubsByTenantId("name:NonExistentClub", tenantId, pageable))
                .isInstanceOf(com.smart_padel.spvending_management_api.shared.exceptions.NotResourcesFoundException.class)
                .hasMessageContaining("No clubs found for this tenant");

        verify(jpaClubRepository).findAll(any(Specification.class), eq(pageable));
        verify(jpaTenantRepository).existsById(tenantId);
    }

    @Test
    void save_NewClub_ReturnsSavedClub() {
        when(jpaTenantRepository.findById(tenantId)).thenReturn(Optional.of(tenantEntity));
        when(jpaClubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity);
        Club savedClub = clubRepositoryAdapter.save(tenantId, club);
        assertEquals(club, savedClub);
        verify(jpaTenantRepository, times(1)).findById(tenantId);
        verify(jpaClubRepository, times(1)).save(any(ClubEntity.class));

    }

    @Test
    void findById_shouldReturnClub() {
        when(jpaClubRepository.findById(clubId)).thenReturn(Optional.of(clubEntity));
        Club result = clubRepositoryAdapter.findById(clubId);
        assertThat(result.getClubId()).isEqualTo(clubId);
        verify(jpaClubRepository).findById(clubId);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(jpaClubRepository.findById(clubId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> clubRepositoryAdapter.findById(clubId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no club with that Id");
    }

    @Test
    void update_shouldModifyClubAndReturnUpdated() {
        when(jpaClubRepository.existsById(clubId)).thenReturn(true);
        when(jpaTenantRepository.findById(tenantId)).thenReturn(Optional.of(tenantEntity));
        when(jpaClubRepository.save(clubEntity)).thenReturn(clubEntity);

        Club result = clubRepositoryAdapter.update(tenantId, clubId, club);

        assertThat(result.getClubId()).isEqualTo(clubId);
        verify(jpaClubRepository).save(clubEntity);
    }

    @Test
    void deleteById_ExistingClubId() {
        when(jpaClubRepository.existsById(clubId)).thenReturn(true);
        doNothing().when(jpaClubRepository).deleteById(clubId);
        clubRepositoryAdapter.deleteById(clubId);
        verify(jpaClubRepository, times(1)).existsById(clubId);
        verify(jpaClubRepository, times(1)).deleteById(clubId);
    }

    @Test
    void deleteById_NonExistingClubId_ThrowsResourceNotFoundException() {
        when(jpaClubRepository.existsById(clubId)).thenReturn(false);
        assertThatThrownBy(() -> clubRepositoryAdapter.deleteById(clubId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("The club does not exist");
        verify(jpaClubRepository, times(1)).existsById(clubId);
        verify(jpaClubRepository, never()).deleteById(tenantId);
    }
}
