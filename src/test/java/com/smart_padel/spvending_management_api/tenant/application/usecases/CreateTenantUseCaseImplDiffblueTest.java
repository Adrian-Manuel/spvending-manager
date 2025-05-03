package com.smart_padel.spvending_management_api.tenant.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant.TenantBuilder;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CreateTenantUseCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CreateTenantUseCaseImplDiffblueTest {
    @Autowired
    private CreateTenantUseCaseImpl createTenantUseCaseImpl;

    @MockitoBean
    private TenantRepositoryPort tenantRepositoryPort;

    @Test
    @DisplayName("Test createTenant(Tenant)")
    void testCreateTenant() {
        // Arrange
        TenantBuilder cifResult = Tenant.builder().cif("Cif");
        TenantBuilder remarkResult = cifResult.clubs(new ArrayList<>())
                .email("jane.doe@example.org")
                .micronId("42")
                .name("Name")
                .phone("6625550144")
                .remark("Remark");
        UUID tenantId = UUID.randomUUID();
        TenantBuilder tenantIdResult = remarkResult.tenantId(tenantId);
        Tenant buildResult = tenantIdResult.userManagers(new ArrayList<>()).build();
        when(tenantRepositoryPort.save(Mockito.<Tenant>any())).thenReturn(buildResult);

        // Act
        Tenant actualCreateTenantResult = createTenantUseCaseImpl.createTenant(new Tenant());

        // Assert
        verify(tenantRepositoryPort).save(isA(Tenant.class));
        assertEquals("42", actualCreateTenantResult.getMicronId());
        assertEquals("6625550144", actualCreateTenantResult.getPhone());
        assertEquals("Cif", actualCreateTenantResult.getCif());
        assertEquals("Name", actualCreateTenantResult.getName());
        assertEquals("Remark", actualCreateTenantResult.getRemark());
        assertEquals("jane.doe@example.org", actualCreateTenantResult.getEmail());
        assertNull(actualCreateTenantResult.getAddress());
        assertTrue(actualCreateTenantResult.getClubs().isEmpty());
        assertTrue(actualCreateTenantResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualCreateTenantResult.getTenantId());
    }
}
