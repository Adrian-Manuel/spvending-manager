package com.smart_padel.spvending_management_api.tenant.infrastructure.utils;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class TenantHelperAdapterTest {
    private JpaTenantRepository tenantRepository;

    @BeforeEach
    void setUp() {
        tenantRepository = mock(JpaTenantRepository.class);
    }

    @Test
    void getTenantOrThrow_shouldReturnTenantEntity() {
        UUID tenantId = UUID.randomUUID();
        TenantEntity tenant = TenantEntity.builder().tenantId(tenantId).name("Test").build();
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(tenant));
        TenantEntity result = TenantHelperAdapter.getTenantOrThrow(tenantRepository, tenantId);
        assertNotNull(result);
        assertEquals("Test", result.getName());
    }

    @Test
    void getTenantOrThrow_shouldThrowExceptionIfNotFound() {
        UUID tenantId = UUID.randomUUID();
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> TenantHelperAdapter.getTenantOrThrow(tenantRepository, tenantId));
        assertEquals("The requested tenant was not found", exception.getMessage());
    }

    @Test
    void validateTenantExists_shouldPassIfTenantExists() {
        UUID tenantId = UUID.randomUUID();
        when(tenantRepository.existsById(tenantId)).thenReturn(true);
        assertDoesNotThrow(() -> TenantHelperAdapter.validateTenantExists(tenantRepository, tenantId));
    }

    @Test
    void validateTenantExists_shouldThrowIfTenantDoesNotExist() {
        UUID tenantId = UUID.randomUUID();
        when(tenantRepository.existsById(tenantId)).thenReturn(false);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> TenantHelperAdapter.validateTenantExists(tenantRepository, tenantId));
        assertEquals("The tenant does not exist", exception.getMessage());
    }

    @Test
    void validateTenantNameNotExists_shouldPassIfNameNotExists() {
        when(tenantRepository.existsByName("UniqueTenant")).thenReturn(false);
        assertDoesNotThrow(() -> TenantHelperAdapter.validateTenantNameNotExists(tenantRepository, "UniqueTenant"));
    }

    @Test
    void validateTenantNameNotExists_shouldThrowIfNameExists() {
        when(tenantRepository.existsByName("DuplicateTenant")).thenReturn(true);
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> TenantHelperAdapter.validateTenantNameNotExists(tenantRepository, "DuplicateTenant"));
        assertEquals("There is already a tenant with that name", exception.getMessage());
    }
}
