package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class CreateTenantUseCaseImplTest {
    private TenantRepositoryPort tenantRepositoryPort;
    private CreateTenantUseCaseImpl createTenantUseCase;

    @BeforeEach
    void setUp() {
        tenantRepositoryPort = mock(TenantRepositoryPort.class);
        createTenantUseCase = new CreateTenantUseCaseImpl(tenantRepositoryPort);
    }

    @Test
    void shouldCreateTenantSuccessfully() {
        // Arrange
        UUID tenantId = UUID.randomUUID();
        Tenant inputTenant = Tenant.builder()
                .tenantId(tenantId)
                .name("Test Company")
                .cif("B12345678")
                .address("123 Main St")
                .phone("123456789")
                .email("test@example.com")
                .remark("Test remark")
                .micronId("MICRON123")
                .build();

        when(tenantRepositoryPort.save(inputTenant)).thenReturn(inputTenant);

        // Act
        Tenant result = createTenantUseCase.createTenant(inputTenant);

        // Assert
        assertNotNull(result);
        assertEquals(inputTenant, result);
        verify(tenantRepositoryPort, times(1)).save(inputTenant);
    }
}
