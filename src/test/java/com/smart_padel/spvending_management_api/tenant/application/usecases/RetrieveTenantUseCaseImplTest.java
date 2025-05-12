package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class RetrieveTenantUseCaseImplTest {
    private TenantRepositoryPort tenantRepositoryPort;
    private RetrieveTenantUseCaseImpl retrieveTenantUseCase;

    @BeforeEach
    void setUp() {
        tenantRepositoryPort = mock(TenantRepositoryPort.class);
        retrieveTenantUseCase = new RetrieveTenantUseCaseImpl(tenantRepositoryPort);
    }

    @Test
    void shouldReturnAllTenantsWithoutSearch() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Tenant> tenants = List.of(
                Tenant.builder().name("Tenant A").build(),
                Tenant.builder().name("Tenant B").build()
        );
        Page<Tenant> tenantPage = new PageImpl<>(tenants, pageable, tenants.size());

        when(tenantRepositoryPort.findAll(pageable)).thenReturn(tenantPage);

        Page<Tenant> result = retrieveTenantUseCase.getAllTenants(pageable);

        assertEquals(2, result.getContent().size());
        verify(tenantRepositoryPort, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnAllTenantsWithSearch() {
        Pageable pageable = PageRequest.of(0, 10);
        String search = "Tenant";
        List<Tenant> tenants = List.of(
                Tenant.builder().name("Tenant A").build()
        );
        Page<Tenant> tenantPage = new PageImpl<>(tenants, pageable, tenants.size());

        when(tenantRepositoryPort.findAll(search, pageable)).thenReturn(tenantPage);

        Page<Tenant> result = retrieveTenantUseCase.getAllTenants(search, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Tenant A", result.getContent().get(0).getName());
        verify(tenantRepositoryPort, times(1)).findAll(search, pageable);
    }

    @Test
    void shouldReturnTenantById() {
        UUID tenantId = UUID.randomUUID();
        Tenant expectedTenant = Tenant.builder()
                .tenantId(tenantId)
                .name("Tenant X")
                .build();

        when(tenantRepositoryPort.findById(tenantId)).thenReturn(expectedTenant);

        Tenant result = retrieveTenantUseCase.getTenantById(tenantId);

        assertEquals(expectedTenant, result);
        verify(tenantRepositoryPort, times(1)).findById(tenantId);
    }

    @Test
    void shouldReturnAllTenantsSummary() {
        List<Tenant> tenants = List.of(
                Tenant.builder().name("Summary A").build(),
                Tenant.builder().name("Summary B").build()
        );

        when(tenantRepositoryPort.findAllTenantsSummary()).thenReturn(tenants);

        List<Tenant> result = retrieveTenantUseCase.getAllTenantsSummary();

        assertEquals(2, result.size());
        verify(tenantRepositoryPort, times(1)).findAllTenantsSummary();
    }
}
