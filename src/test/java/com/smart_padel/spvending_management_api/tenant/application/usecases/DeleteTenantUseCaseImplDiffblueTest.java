package com.smart_padel.spvending_management_api.tenant.application.usecases;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DeleteTenantUseCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DeleteTenantUseCaseImplDiffblueTest {
    @Autowired
    private DeleteTenantUseCaseImpl deleteTenantUseCaseImpl;

    @MockitoBean
    private TenantRepositoryPort tenantRepositoryPort;

    /**
     * Test {@link DeleteTenantUseCaseImpl#deleteTenant(UUID)}.
     * <p>
     * Method under test: {@link DeleteTenantUseCaseImpl#deleteTenant(UUID)}
     */
    @Test
    @DisplayName("Test deleteTenant(UUID)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void DeleteTenantUseCaseImpl.deleteTenant(UUID)"})
    void testDeleteTenant() {
        // Arrange
        doNothing().when(tenantRepositoryPort).deleteById(Mockito.<UUID>any());

        // Act
        deleteTenantUseCaseImpl.deleteTenant(UUID.randomUUID());

        // Assert
        verify(tenantRepositoryPort).deleteById(isA(UUID.class));
    }
}
