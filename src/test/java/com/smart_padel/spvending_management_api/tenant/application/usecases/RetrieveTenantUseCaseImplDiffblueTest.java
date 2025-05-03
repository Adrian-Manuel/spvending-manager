package com.smart_padel.spvending_management_api.tenant.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RetrieveTenantUseCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RetrieveTenantUseCaseImplDiffblueTest {
    @Autowired
    private RetrieveTenantUseCaseImpl retrieveTenantUseCaseImpl;

    @MockitoBean
    private TenantRepositoryPort tenantRepositoryPort;

    @Test
    @DisplayName("Test getAllTenants(Pageable) with 'pageable'")
    @MethodsUnderTest({"Page RetrieveTenantUseCaseImpl.getAllTenants(Pageable)"})
    void testGetAllTenantsWithPageable() {
        // Arrange
        PageImpl<Tenant> pageImpl = new PageImpl<>(new ArrayList<>());
        when(tenantRepositoryPort.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<Tenant> actualAllTenants = retrieveTenantUseCaseImpl.getAllTenants(null);

        // Assert
        verify(tenantRepositoryPort).findAll(isNull());
        assertInstanceOf(PageImpl.class, actualAllTenants);
        assertTrue(actualAllTenants.toList().isEmpty());
        assertSame(pageImpl, actualAllTenants);
    }


    @Test
    @DisplayName("Test getAllTenants(String, Pageable) with 'search', 'pageable'")
    @MethodsUnderTest({"Page RetrieveTenantUseCaseImpl.getAllTenants(String, Pageable)"})
    void testGetAllTenantsWithSearchPageable() {
        // Arrange
        PageImpl<Tenant> pageImpl = new PageImpl<>(new ArrayList<>());
        when(tenantRepositoryPort.findAll(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<Tenant> actualAllTenants = retrieveTenantUseCaseImpl.getAllTenants("Search", null);

        // Assert
        verify(tenantRepositoryPort).findAll(eq("Search"), isNull());
        assertInstanceOf(PageImpl.class, actualAllTenants);
        assertTrue(actualAllTenants.toList().isEmpty());
        assertSame(pageImpl, actualAllTenants);
    }
}
