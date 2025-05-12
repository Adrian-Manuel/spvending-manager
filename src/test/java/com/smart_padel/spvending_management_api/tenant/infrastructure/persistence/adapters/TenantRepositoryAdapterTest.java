package com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.adapters;

import com.smart_padel.spvending_management_api.shared.exceptions.NotResourcesFoundException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantRepositoryAdapterTest {

    @Mock
    private JpaTenantRepository jpaTenantRepository;

    @InjectMocks
    private TenantRepositoryAdapter tenantRepositoryAdapter;

    private UUID tenantId;
    private Tenant tenant;
    private TenantEntity tenantEntity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        tenantId = UUID.randomUUID();
        tenant = Tenant.builder()
                .tenantId(tenantId)
                .name("Test Tenant")
                .cif("TESTCIF")
                .address("Test Address")
                .phone("123456789")
                .email("test@example.com")
                .remark("Test Remark")
                .micronId("TESTMICRONID")
                .clubsCount(0)
                .managers(Collections.emptyList())
                .build();

        tenantEntity = TenantEntity.fromDomainModel(tenant);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void save_NewTenant_ReturnsSavedTenant() {
        when(jpaTenantRepository.existsByName(tenant.getName())).thenReturn(false);
        when(jpaTenantRepository.save(any(TenantEntity.class))).thenReturn(tenantEntity);
        Tenant savedTenant = tenantRepositoryAdapter.save(tenant);
        assertEquals(tenant, savedTenant);
        verify(jpaTenantRepository, times(1)).existsByName(tenant.getName());
        verify(jpaTenantRepository, times(1)).save(any(TenantEntity.class));
    }

    @Test
    void save_ExistingTenantName_ThrowsResourceAlreadyExistsException() {
        when(jpaTenantRepository.existsByName(tenant.getName())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, () -> tenantRepositoryAdapter.save(tenant));
        verify(jpaTenantRepository, times(1)).existsByName(tenant.getName());
        verify(jpaTenantRepository, never()).save(any(TenantEntity.class));
    }

    @Test
    void findById_ExistingTenantId_ReturnsTenant() {
        when(jpaTenantRepository.findById(tenantId)).thenReturn(Optional.of(tenantEntity));
        Tenant foundTenant = tenantRepositoryAdapter.findById(tenantId);
        assertEquals(tenant, foundTenant);
        verify(jpaTenantRepository, times(1)).findById(tenantId);
    }

    @Test
    void findById_NonExistingTenantId_ThrowsResourceNotFoundException() {
        when(jpaTenantRepository.findById(tenantId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tenantRepositoryAdapter.findById(tenantId));
        verify(jpaTenantRepository, times(1)).findById(tenantId);
    }

    @Test
    void findAll_NoTenants_ThrowsNotResourcesFoundException() {
        when(jpaTenantRepository.findAll(pageable)).thenReturn(Page.empty());
        assertThrows(NotResourcesFoundException.class, () -> tenantRepositoryAdapter.findAll(pageable));
        verify(jpaTenantRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAll_ExistingTenants_ReturnsPageOfTenants() {
        Page<TenantEntity> entityPage = new PageImpl<>(Collections.singletonList(tenantEntity), pageable, 1);
        when(jpaTenantRepository.findAll(pageable)).thenReturn(entityPage);
        Page<Tenant> tenantPage = tenantRepositoryAdapter.findAll(pageable);
        assertEquals(1, tenantPage.getContent().size());
        assertEquals(tenant, tenantPage.getContent().getFirst());
        verify(jpaTenantRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAll_withSearch_NoTenantsFound_ThrowsNotResourcesFoundException() {
        String search = "nonExistent";
        when(jpaTenantRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(Page.empty());
        assertThrows(NotResourcesFoundException.class, () -> tenantRepositoryAdapter.findAll(search, pageable));
        verify(jpaTenantRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_withSearch_TenantsFound_ReturnsPageOfTenants() {
        String search = "test";
        Page<TenantEntity> entityPage = new PageImpl<>(Collections.singletonList(tenantEntity), pageable, 1);
        when(jpaTenantRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(entityPage);
        Page<Tenant> tenantPage = tenantRepositoryAdapter.findAll(search, pageable);
        assertEquals(1, tenantPage.getContent().size());
        assertEquals(tenant, tenantPage.getContent().getFirst());
        verify(jpaTenantRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAllTenantsSummary_NoTenants_ThrowsNotResourcesFoundException() {
        when(jpaTenantRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotResourcesFoundException.class, () -> tenantRepositoryAdapter.findAllTenantsSummary());
        verify(jpaTenantRepository, times(1)).findAll();
    }

    @Test
    void findAllTenantsSummary_ExistingTenants_ReturnsListOfTenants() {
        when(jpaTenantRepository.findAll()).thenReturn(Collections.singletonList(tenantEntity));
        List<Tenant> tenants = tenantRepositoryAdapter.findAllTenantsSummary();
        assertEquals(1, tenants.size());
        assertEquals(tenant, tenants.get(0));
        verify(jpaTenantRepository, times(1)).findAll();
    }

    @Test
    void update_ExistingTenantId_UpdatesAndReturnsTenant() {
        Tenant updatedTenant = Tenant.builder()
                .tenantId(tenantId)
                .name("Updated Tenant")
                .cif("UPDATEDCIF")
                .address("Updated Address")
                .phone("987654321")
                .email("updated@example.com")
                .remark("Updated Remark")
                .micronId("UPDATEDMICRONID")
                .clubsCount(0)
                .managers(Collections.emptyList())
                .build();
        TenantEntity updatedEntity = TenantEntity.fromDomainModel(updatedTenant);

        when(jpaTenantRepository.existsById(tenantId)).thenReturn(true);
        when(jpaTenantRepository.save(updatedEntity)).thenReturn(updatedEntity);

        Tenant result = tenantRepositoryAdapter.update(tenantId, updatedTenant);

        assertEquals(updatedTenant, result);
        verify(jpaTenantRepository, times(1)).existsById(tenantId);
        verify(jpaTenantRepository, times(1)).save(updatedEntity);
    }

    @Test
    void update_NonExistingTenantId_ThrowsResourceNotFoundException() {
        when(jpaTenantRepository.existsById(tenantId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> tenantRepositoryAdapter.update(tenantId, tenant));
        verify(jpaTenantRepository, times(1)).existsById(tenantId);
        verify(jpaTenantRepository, never()).existsByName(anyString());
        verify(jpaTenantRepository, never()).save(any());
    }



    @Test
    void deleteById_ExistingTenantId() {
        when(jpaTenantRepository.existsById(tenantId)).thenReturn(true);
        doNothing().when(jpaTenantRepository).deleteById(tenantId);
        tenantRepositoryAdapter.deleteById(tenantId);
        verify(jpaTenantRepository, times(1)).existsById(tenantId);
        verify(jpaTenantRepository, times(1)).deleteById(tenantId);
    }

    @Test
    void deleteById_NonExistingTenantId_ThrowsResourceNotFoundException() {
        when(jpaTenantRepository.existsById(tenantId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> tenantRepositoryAdapter.deleteById(tenantId));
        verify(jpaTenantRepository, times(1)).existsById(tenantId);
        verify(jpaTenantRepository, never()).deleteById(tenantId);
    }
}