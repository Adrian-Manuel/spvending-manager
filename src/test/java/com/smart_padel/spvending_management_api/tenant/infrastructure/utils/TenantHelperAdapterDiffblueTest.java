package com.smart_padel.spvending_management_api.tenant.infrastructure.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TenantHelperAdapterDiffblueTest {
    @Test
    @DisplayName("Test getTenantOrThrow(JpaTenantRepository, UUID); given empty; when JpaTenantRepository findById(Object) return empty")
    @MethodsUnderTest({"TenantEntity TenantHelperAdapter.getTenantOrThrow(JpaTenantRepository, UUID)"})
    void testGetTenantOrThrow_givenEmpty_whenJpaTenantRepositoryFindByIdReturnEmpty() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        Optional<TenantEntity> emptyResult = Optional.empty();
        when(repo.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> TenantHelperAdapter.getTenantOrThrow(repo, UUID.randomUUID()));
        verify(repo).findById(isA(UUID.class));
    }
    @Test
    @DisplayName("Test getTenantOrThrow(JpaTenantRepository, UUID); given ResourceNotFoundException(String) with message is 'An error occurred'")
    @MethodsUnderTest({"TenantEntity TenantHelperAdapter.getTenantOrThrow(JpaTenantRepository, UUID)"})
    void testGetTenantOrThrow_givenResourceNotFoundExceptionWithMessageIsAnErrorOccurred() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.findById(Mockito.<UUID>any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> TenantHelperAdapter.getTenantOrThrow(repo, UUID.randomUUID()));
        verify(repo).findById(isA(UUID.class));
    }


    @Test
    @DisplayName("Test getTenantOrThrow(JpaTenantRepository, UUID); then return Address is '42 Main St'")
    @MethodsUnderTest({"TenantEntity TenantHelperAdapter.getTenantOrThrow(JpaTenantRepository, UUID)"})
    void testGetTenantOrThrow_thenReturnAddressIs42MainSt() {
        // Arrange
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Cif");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Name");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Remark");
        UUID tenantId = UUID.randomUUID();
        tenantEntity.setTenantId(tenantId);
        tenantEntity.setUserManagerEntities(new ArrayList<>());
        Optional<TenantEntity> ofResult = Optional.of(tenantEntity);
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act
        TenantEntity actualTenantOrThrow = TenantHelperAdapter.getTenantOrThrow(repo, UUID.randomUUID());

        // Assert
        verify(repo).findById(isA(UUID.class));
        assertEquals("42 Main St", actualTenantOrThrow.getAddress());
        assertEquals("42", actualTenantOrThrow.getMicronId());
        assertEquals("6625550144", actualTenantOrThrow.getPhone());
        assertEquals("Cif", actualTenantOrThrow.getCif());
        assertEquals("Name", actualTenantOrThrow.getName());
        assertEquals("Remark", actualTenantOrThrow.getRemark());
        assertEquals("jane.doe@example.org", actualTenantOrThrow.getEmail());
        assertTrue(actualTenantOrThrow.getClubs().isEmpty());
        assertTrue(actualTenantOrThrow.getUserManagerEntities().isEmpty());
        assertSame(tenantId, actualTenantOrThrow.getTenantId());
    }
    @Test
    @DisplayName("Test validateTenantExists(JpaTenantRepository, UUID)")
    @MethodsUnderTest({"void TenantHelperAdapter.validateTenantExists(JpaTenantRepository, UUID)"})
    void testValidateTenantExists() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.existsById(Mockito.<UUID>any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> TenantHelperAdapter.validateTenantExists(repo, UUID.randomUUID()));
        verify(repo).existsById(isA(UUID.class));
    }
    @Test
    @DisplayName("Test validateTenantExists(JpaTenantRepository, UUID); given 'false'; when JpaTenantRepository existsById(Object) return 'false'")
    @MethodsUnderTest({"void TenantHelperAdapter.validateTenantExists(JpaTenantRepository, UUID)"})
    void testValidateTenantExists_givenFalse_whenJpaTenantRepositoryExistsByIdReturnFalse() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.existsById(Mockito.<UUID>any())).thenReturn(false);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> TenantHelperAdapter.validateTenantExists(repo, UUID.randomUUID()));
        verify(repo).existsById(isA(UUID.class));
    }
    @Test
    @DisplayName("Test validateTenantExists(JpaTenantRepository, UUID); given 'true'; when JpaTenantRepository existsById(Object) return 'true'")
    @MethodsUnderTest({"void TenantHelperAdapter.validateTenantExists(JpaTenantRepository, UUID)"})
    void testValidateTenantExists_givenTrue_whenJpaTenantRepositoryExistsByIdReturnTrue() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.existsById(Mockito.<UUID>any())).thenReturn(true);

        // Act
        TenantHelperAdapter.validateTenantExists(repo, UUID.randomUUID());

        // Assert
        verify(repo).existsById(isA(UUID.class));
    }
    @Test
    @DisplayName("Test validateTenantNameNotExists(JpaTenantRepository, String); given 'false'")
    @MethodsUnderTest({"void TenantHelperAdapter.validateTenantNameNotExists(JpaTenantRepository, String)"})
    void testValidateTenantNameNotExists_givenFalse() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.existsByName(Mockito.<String>any())).thenReturn(false);

        // Act
        TenantHelperAdapter.validateTenantNameNotExists(repo, "Name");

        // Assert
        verify(repo).existsByName(eq("Name"));
    }
    @Test
    @DisplayName("Test validateTenantNameNotExists(JpaTenantRepository, String); then throw ResourceAlreadyExistsException")
    @MethodsUnderTest({"void TenantHelperAdapter.validateTenantNameNotExists(JpaTenantRepository, String)"})
    void testValidateTenantNameNotExists_thenThrowResourceAlreadyExistsException() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.existsByName(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(ResourceAlreadyExistsException.class,
                () -> TenantHelperAdapter.validateTenantNameNotExists(repo, "Name"));
        verify(repo).existsByName(eq("Name"));
    }

    /**
     * Test {@link TenantHelperAdapter#validateTenantNameNotExists(JpaTenantRepository, String)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link TenantHelperAdapter#validateTenantNameNotExists(JpaTenantRepository, String)}
     */
    @Test
    @DisplayName("Test validateTenantNameNotExists(JpaTenantRepository, String); then throw ResourceNotFoundException")
    @MethodsUnderTest({"void TenantHelperAdapter.validateTenantNameNotExists(JpaTenantRepository, String)"})
    void testValidateTenantNameNotExists_thenThrowResourceNotFoundException() {
        // Arrange
        JpaTenantRepository repo = mock(JpaTenantRepository.class);
        when(repo.existsByName(Mockito.<String>any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> TenantHelperAdapter.validateTenantNameNotExists(repo, "Name"));
        verify(repo).existsByName(eq("Name"));
    }
}
