package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.mockito.Mockito.*;
class DeleteTenantUseCaseImplTest {
    private TenantRepositoryPort tenantRepositoryPort;
    private DeleteTenantUseCaseImpl deleteTenantUseCase;

    @BeforeEach
    void setUp() {
        tenantRepositoryPort = mock(TenantRepositoryPort.class);
        deleteTenantUseCase = new DeleteTenantUseCaseImpl(tenantRepositoryPort);
    }

    @Test
    void shouldDeleteTenantSuccessfully() {
        // Arrange
        UUID tenantId = UUID.randomUUID();

        // Act
        deleteTenantUseCase.deleteTenant(tenantId);

        // Assert
        verify(tenantRepositoryPort, times(1)).deleteById(tenantId);
    }

    @Test
    void shouldNotCallDeleteIfTenantIdIsNull() {
        // Act
        deleteTenantUseCase.deleteTenant(null);

        // Assert
        verify(tenantRepositoryPort, never()).deleteById(any(UUID.class));
    }
}
