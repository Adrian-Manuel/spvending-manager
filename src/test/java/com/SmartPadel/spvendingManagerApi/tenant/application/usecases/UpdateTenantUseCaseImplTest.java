package com.SmartPadel.spvendingManagerApi.tenant.application.usecases;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.out.TenantRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UpdateTenantUseCaseImplTest {

    @Mock
    private TenantRepositoryPort tenantRepositoryPort;

    @InjectMocks
    private UpdateTenantUseCaseImpl updateTenantUseCaseImpl;

    private final UUID tenantId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");

    @Test
    @DisplayName("Test updateTenant(UUID, Tenant) - Successful update")
    @Tag("Unit")
    void testUpdateTenant_Success() {
        // Arrange
        Tenant updatedTenantDetails = Tenant.builder()
                .name("Updated Name")
                .cif("Updated CIF")
                .address("Updated Address")
                .phone("999888777")
                .email("updated@example.com")
                .remark("Updated Remark")
                .micronId("UpdatedMicron")
                .clubs(new ArrayList<>())
                .userManagerEntities(new ArrayList<>())
                .build();

        Tenant expectedUpdatedTenant = Tenant.builder()
                .tenantId(tenantId)
                .name("Updated Name")
                .cif("Updated CIF")
                .address("Updated Address")
                .phone("999888777")
                .email("updated@example.com")
                .remark("Updated Remark")
                .micronId("UpdatedMicron")
                .clubs(new ArrayList<>())
                .userManagerEntities(new ArrayList<>())
                .build();

        when(tenantRepositoryPort.update(eq(tenantId), any(Tenant.class))).thenReturn(expectedUpdatedTenant);

        // Act
        Tenant actualUpdatedTenant = updateTenantUseCaseImpl.updateTenant(tenantId, updatedTenantDetails);

        // Assert
        verify(tenantRepositoryPort).update(eq(tenantId), any(Tenant.class));
        assertEquals(expectedUpdatedTenant, actualUpdatedTenant);
    }

    @Test
    @DisplayName("Test updateTenant(UUID, Tenant) - Tenant not found (simulated by repository returning null)")
    @Tag("Unit")
    void testUpdateTenant_NotFound_RepoReturnsNull() {
        // Arrange
        Tenant updatedTenantDetails = Tenant.builder().name("Updated Name").build();
        when(tenantRepositoryPort.update(eq(tenantId), any(Tenant.class))).thenReturn(null);
        Tenant actualResult = updateTenantUseCaseImpl.updateTenant(tenantId, updatedTenantDetails);
        assertNull(actualResult);
        verify(tenantRepositoryPort).update(eq(tenantId), any(Tenant.class));
    }


    @Test
    @DisplayName("Test updateTenant(UUID, Tenant) - Verify arguments passed to repository")
    @Tag("Unit")
    void testUpdateTenant_VerifyRepositoryArguments() {
        // Arrange
        UUID providedId = UUID.randomUUID();
        Tenant updatedTenantDetails = Tenant.builder()
                .name("Specific Name")
                .cif("Specific CIF")
                .address("Specific Address")
                .phone("111222333")
                .email("specific@example.com")
                .remark("Specific Remark")
                .micronId("SMID456")
                .clubs(List.of())
                .userManagerEntities(List.of())
                .build();

        // Act
        updateTenantUseCaseImpl.updateTenant(providedId, updatedTenantDetails);

        // Assert
        ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
        ArgumentCaptor<Tenant> tenantCaptor = ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepositoryPort).update(idCaptor.capture(), tenantCaptor.capture());

        assertEquals(providedId, idCaptor.getValue());
        assertEquals("Specific Name", tenantCaptor.getValue().getName());
        assertEquals("Specific CIF", tenantCaptor.getValue().getCif());
        assertEquals("Specific Address", tenantCaptor.getValue().getAddress());
        assertEquals("111222333", tenantCaptor.getValue().getPhone());
        assertEquals("specific@example.com", tenantCaptor.getValue().getEmail());
        assertEquals("Specific Remark", tenantCaptor.getValue().getRemark());
        assertEquals("SMID456", tenantCaptor.getValue().getMicronId());
        assertEquals(List.of(), tenantCaptor.getValue().getClubs());
        assertEquals(List.of(), tenantCaptor.getValue().getUserManagerEntities());
    }
}