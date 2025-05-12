package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class UpdateTenantUseCaseImplTest {
    private TenantRepositoryPort tenantRepositoryPort;
    private UpdateTenantUseCaseImpl updateTenantUseCase;

    @BeforeEach
    void setUp() {
        tenantRepositoryPort = mock(TenantRepositoryPort.class);
        updateTenantUseCase = new UpdateTenantUseCaseImpl(tenantRepositoryPort);
    }

    @Test
    void shouldUpdateTenantSuccessfully() {
        // Arrange
        UUID tenantId = UUID.randomUUID();
        Tenant updatedTenant = Tenant.builder()
                .tenantId(tenantId)
                .name("Updated Company")
                .cif("B87654321")
                .address("456 Updated St")
                .phone("987654321")
                .email("updated@example.com")
                .remark("Updated remark")
                .micronId("MICRON456")
                .build();

        when(tenantRepositoryPort.update(tenantId, updatedTenant)).thenReturn(updatedTenant);

        // Act
        Tenant result = updateTenantUseCase.updateTenant(tenantId, updatedTenant);

        // Assert
        assertEquals(updatedTenant, result);
        verify(tenantRepositoryPort, times(1)).update(tenantId, updatedTenant);
    }
}
