package com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.adapters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant.TenantBuilder;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.diffblue.cover.annotations.MethodsUnderTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TenantRepositoryAdapter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TenantRepositoryAdapterDiffblueTest {
    @MockitoBean
    private JpaTenantRepository jpaTenantRepository;
    @Autowired
    private TenantRepositoryAdapter tenantRepositoryAdapter;
    @Test
    @DisplayName("Test save(Tenant); given '42 Main St'; then return Address is '42 Main St'")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.save(Tenant)"})
    void testSave_given42MainSt_thenReturnAddressIs42MainSt() {
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
        when(jpaTenantRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(jpaTenantRepository.save(Mockito.<TenantEntity>any())).thenReturn(tenantEntity);
        Tenant tenant = mock(Tenant.class);
        when(tenant.getAddress()).thenReturn("42 Main St");
        when(tenant.getCif()).thenReturn("Cif");
        when(tenant.getEmail()).thenReturn("jane.doe@example.org");
        when(tenant.getMicronId()).thenReturn("42");
        when(tenant.getPhone()).thenReturn("6625550144");
        when(tenant.getRemark()).thenReturn("Remark");
        when(tenant.getClubs()).thenReturn(new ArrayList<>());
        when(tenant.getUserManagers()).thenReturn(new ArrayList<>());
        when(tenant.getTenantId()).thenReturn(UUID.randomUUID());
        when(tenant.getName()).thenReturn("Name");

        // Act
        Tenant actualSaveResult = tenantRepositoryAdapter.save(tenant);

        // Assert
        verify(tenant).getAddress();
        verify(tenant).getCif();
        verify(tenant).getClubs();
        verify(tenant).getEmail();
        verify(tenant).getMicronId();
        verify(tenant, atLeast(1)).getName();
        verify(tenant).getPhone();
        verify(tenant).getRemark();
        verify(tenant).getTenantId();
        verify(tenant).getUserManagers();
        verify(jpaTenantRepository).existsByName(eq("Name"));
        verify(jpaTenantRepository).save(isA(TenantEntity.class));
        assertEquals("42 Main St", actualSaveResult.getAddress());
        assertEquals("42", actualSaveResult.getMicronId());
        assertEquals("6625550144", actualSaveResult.getPhone());
        assertEquals("Cif", actualSaveResult.getCif());
        assertEquals("Name", actualSaveResult.getName());
        assertEquals("Remark", actualSaveResult.getRemark());
        assertEquals("jane.doe@example.org", actualSaveResult.getEmail());
        assertTrue(actualSaveResult.getClubs().isEmpty());
        assertTrue(actualSaveResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualSaveResult.getTenantId());
    }
    @Test
    @DisplayName("Test save(Tenant); then return Address is 'null'")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.save(Tenant)"})
    void testSave_thenReturnAddressIsNull() {
        // Arrange
        TenantEntity tenantEntity = mock(TenantEntity.class);
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
        when(tenantEntity.toDomainModel()).thenReturn(buildResult);
        doNothing().when(tenantEntity).setAddress(Mockito.<String>any());
        doNothing().when(tenantEntity).setCif(Mockito.<String>any());
        doNothing().when(tenantEntity).setClubs(Mockito.<List<Club>>any());
        doNothing().when(tenantEntity).setEmail(Mockito.<String>any());
        doNothing().when(tenantEntity).setMicronId(Mockito.<String>any());
        doNothing().when(tenantEntity).setName(Mockito.<String>any());
        doNothing().when(tenantEntity).setPhone(Mockito.<String>any());
        doNothing().when(tenantEntity).setRemark(Mockito.<String>any());
        doNothing().when(tenantEntity).setTenantId(Mockito.<UUID>any());
        doNothing().when(tenantEntity).setUserManagerEntities(Mockito.<List<UserManager>>any());
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Cif");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Name");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Remark");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());
        when(jpaTenantRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(jpaTenantRepository.save(Mockito.<TenantEntity>any())).thenReturn(tenantEntity);
        Tenant tenant = mock(Tenant.class);
        when(tenant.getAddress()).thenReturn("42 Main St");
        when(tenant.getCif()).thenReturn("Cif");
        when(tenant.getEmail()).thenReturn("jane.doe@example.org");
        when(tenant.getMicronId()).thenReturn("42");
        when(tenant.getPhone()).thenReturn("6625550144");
        when(tenant.getRemark()).thenReturn("Remark");
        when(tenant.getClubs()).thenReturn(new ArrayList<>());
        when(tenant.getUserManagers()).thenReturn(new ArrayList<>());
        when(tenant.getTenantId()).thenReturn(UUID.randomUUID());
        when(tenant.getName()).thenReturn("Name");

        // Act
        Tenant actualSaveResult = tenantRepositoryAdapter.save(tenant);

        // Assert
        verify(tenant).getAddress();
        verify(tenant).getCif();
        verify(tenant).getClubs();
        verify(tenant).getEmail();
        verify(tenant).getMicronId();
        verify(tenant, atLeast(1)).getName();
        verify(tenant).getPhone();
        verify(tenant).getRemark();
        verify(tenant).getTenantId();
        verify(tenant).getUserManagers();
        verify(tenantEntity).setAddress(eq("42 Main St"));
        verify(tenantEntity).setCif(eq("Cif"));
        verify(tenantEntity).setClubs(isA(List.class));
        verify(tenantEntity).setEmail(eq("jane.doe@example.org"));
        verify(tenantEntity).setMicronId(eq("42"));
        verify(tenantEntity).setName(eq("Name"));
        verify(tenantEntity).setPhone(eq("6625550144"));
        verify(tenantEntity).setRemark(eq("Remark"));
        verify(tenantEntity).setTenantId(isA(UUID.class));
        verify(tenantEntity).setUserManagerEntities(isA(List.class));
        verify(tenantEntity).toDomainModel();
        verify(jpaTenantRepository).existsByName(eq("Name"));
        verify(jpaTenantRepository).save(isA(TenantEntity.class));
        assertEquals("42", actualSaveResult.getMicronId());
        assertEquals("6625550144", actualSaveResult.getPhone());
        assertEquals("Cif", actualSaveResult.getCif());
        assertEquals("Name", actualSaveResult.getName());
        assertEquals("Remark", actualSaveResult.getRemark());
        assertEquals("jane.doe@example.org", actualSaveResult.getEmail());
        assertNull(actualSaveResult.getAddress());
        assertTrue(actualSaveResult.getClubs().isEmpty());
        assertTrue(actualSaveResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualSaveResult.getTenantId());
    }
    @Test
    @DisplayName("Test save(Tenant); when Tenant(); then return Address is '42 Main St'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.save(Tenant)"})
    void testSave_whenTenant_thenReturnAddressIs42MainSt() {
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
        when(jpaTenantRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(jpaTenantRepository.save(Mockito.<TenantEntity>any())).thenReturn(tenantEntity);

        // Act
        Tenant actualSaveResult = tenantRepositoryAdapter.save(new Tenant());

        // Assert
        verify(jpaTenantRepository).existsByName(isNull());
        verify(jpaTenantRepository).save(isA(TenantEntity.class));
        assertEquals("42 Main St", actualSaveResult.getAddress());
        assertEquals("42", actualSaveResult.getMicronId());
        assertEquals("6625550144", actualSaveResult.getPhone());
        assertEquals("Cif", actualSaveResult.getCif());
        assertEquals("Name", actualSaveResult.getName());
        assertEquals("Remark", actualSaveResult.getRemark());
        assertEquals("jane.doe@example.org", actualSaveResult.getEmail());
        assertTrue(actualSaveResult.getClubs().isEmpty());
        assertTrue(actualSaveResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualSaveResult.getTenantId());
    }
    @Test
    @DisplayName("Test findById(UUID); given TenantEntity() Address is '42 Main St'; then return Address is '42 Main St'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.findById(UUID)"})
    void testFindById_givenTenantEntityAddressIs42MainSt_thenReturnAddressIs42MainSt() {
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
        when(jpaTenantRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act
        Tenant actualFindByIdResult = tenantRepositoryAdapter.findById(UUID.randomUUID());

        // Assert
        verify(jpaTenantRepository).findById(isA(UUID.class));
        assertEquals("42 Main St", actualFindByIdResult.getAddress());
        assertEquals("42", actualFindByIdResult.getMicronId());
        assertEquals("6625550144", actualFindByIdResult.getPhone());
        assertEquals("Cif", actualFindByIdResult.getCif());
        assertEquals("Name", actualFindByIdResult.getName());
        assertEquals("Remark", actualFindByIdResult.getRemark());
        assertEquals("jane.doe@example.org", actualFindByIdResult.getEmail());
        assertTrue(actualFindByIdResult.getClubs().isEmpty());
        assertTrue(actualFindByIdResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualFindByIdResult.getTenantId());
    }
    @Test
    @DisplayName("Test findById(UUID); then return Address is 'null'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.findById(UUID)"})
    void testFindById_thenReturnAddressIsNull() {
        // Arrange
        TenantEntity tenantEntity = mock(TenantEntity.class);
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
        when(tenantEntity.toDomainModel()).thenReturn(buildResult);
        doNothing().when(tenantEntity).setAddress(Mockito.<String>any());
        doNothing().when(tenantEntity).setCif(Mockito.<String>any());
        doNothing().when(tenantEntity).setClubs(Mockito.<List<Club>>any());
        doNothing().when(tenantEntity).setEmail(Mockito.<String>any());
        doNothing().when(tenantEntity).setMicronId(Mockito.<String>any());
        doNothing().when(tenantEntity).setName(Mockito.<String>any());
        doNothing().when(tenantEntity).setPhone(Mockito.<String>any());
        doNothing().when(tenantEntity).setRemark(Mockito.<String>any());
        doNothing().when(tenantEntity).setTenantId(Mockito.<UUID>any());
        doNothing().when(tenantEntity).setUserManagerEntities(Mockito.<List<UserManager>>any());
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Cif");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Name");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Remark");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());
        Optional<TenantEntity> ofResult = Optional.of(tenantEntity);
        when(jpaTenantRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act
        Tenant actualFindByIdResult = tenantRepositoryAdapter.findById(UUID.randomUUID());

        // Assert
        verify(tenantEntity).setAddress(eq("42 Main St"));
        verify(tenantEntity).setCif(eq("Cif"));
        verify(tenantEntity).setClubs(isA(List.class));
        verify(tenantEntity).setEmail(eq("jane.doe@example.org"));
        verify(tenantEntity).setMicronId(eq("42"));
        verify(tenantEntity).setName(eq("Name"));
        verify(tenantEntity).setPhone(eq("6625550144"));
        verify(tenantEntity).setRemark(eq("Remark"));
        verify(tenantEntity).setTenantId(isA(UUID.class));
        verify(tenantEntity).setUserManagerEntities(isA(List.class));
        verify(tenantEntity).toDomainModel();
        verify(jpaTenantRepository).findById(isA(UUID.class));
        assertEquals("42", actualFindByIdResult.getMicronId());
        assertEquals("6625550144", actualFindByIdResult.getPhone());
        assertEquals("Cif", actualFindByIdResult.getCif());
        assertEquals("Name", actualFindByIdResult.getName());
        assertEquals("Remark", actualFindByIdResult.getRemark());
        assertEquals("jane.doe@example.org", actualFindByIdResult.getEmail());
        assertNull(actualFindByIdResult.getAddress());
        assertTrue(actualFindByIdResult.getClubs().isEmpty());
        assertTrue(actualFindByIdResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualFindByIdResult.getTenantId());
    }
    @Test
    @DisplayName("Test findAll(Pageable) with 'pageable'; then return toList first Address is '42 Main St'")
    @MethodsUnderTest({"Page TenantRepositoryAdapter.findAll(Pageable)"})
    void testFindAllWithPageable_thenReturnToListFirstAddressIs42MainSt() {
        // Arrange
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("No tenants have been added yet");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("No tenants have been added yet");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("No tenants have been added yet");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> content = new ArrayList<>();
        content.add(tenantEntity);
        when(jpaTenantRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));

        // Act
        Page<Tenant> actualFindAllResult = tenantRepositoryAdapter.findAll(null);

        // Assert
        verify(jpaTenantRepository).findAll((Pageable) isNull());
        assertInstanceOf(PageImpl.class, actualFindAllResult);
        List<Tenant> toListResult = actualFindAllResult.toList();
        assertEquals(1, toListResult.size());
        Tenant getResult = toListResult.get(0);
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals("No tenants have been added yet", getResult.getCif());
        assertEquals("No tenants have been added yet", getResult.getName());
        assertEquals("No tenants have been added yet", getResult.getRemark());
    }
    @Test
    @DisplayName("Test findAll(Pageable) with 'pageable'; then return toList first MicronId is '42'")
    @MethodsUnderTest({"Page TenantRepositoryAdapter.findAll(Pageable)"})
    void testFindAllWithPageable_thenReturnToListFirstMicronIdIs42() {
        // Arrange
        TenantEntity tenantEntity = mock(TenantEntity.class);
        TenantBuilder cifResult = Tenant.builder().cif("Cif");
        TenantBuilder remarkResult = cifResult.clubs(new ArrayList<>())
                .email("jane.doe@example.org")
                .micronId("42")
                .name("Name")
                .phone("6625550144")
                .remark("Remark");
        TenantBuilder tenantIdResult = remarkResult.tenantId(UUID.randomUUID());
        Tenant buildResult = tenantIdResult.userManagers(new ArrayList<>()).build();
        when(tenantEntity.toDomainModel()).thenReturn(buildResult);
        doNothing().when(tenantEntity).setAddress(Mockito.<String>any());
        doNothing().when(tenantEntity).setCif(Mockito.<String>any());
        doNothing().when(tenantEntity).setClubs(Mockito.<List<Club>>any());
        doNothing().when(tenantEntity).setEmail(Mockito.<String>any());
        doNothing().when(tenantEntity).setMicronId(Mockito.<String>any());
        doNothing().when(tenantEntity).setName(Mockito.<String>any());
        doNothing().when(tenantEntity).setPhone(Mockito.<String>any());
        doNothing().when(tenantEntity).setRemark(Mockito.<String>any());
        doNothing().when(tenantEntity).setTenantId(Mockito.<UUID>any());
        doNothing().when(tenantEntity).setUserManagerEntities(Mockito.<List<UserManager>>any());
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("No tenants have been added yet");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("No tenants have been added yet");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("No tenants have been added yet");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> content = new ArrayList<>();
        content.add(tenantEntity);
        when(jpaTenantRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));

        // Act
        Page<Tenant> actualFindAllResult = tenantRepositoryAdapter.findAll(null);

        // Assert
        verify(tenantEntity).setAddress(eq("42 Main St"));
        verify(tenantEntity).setCif(eq("No tenants have been added yet"));
        verify(tenantEntity).setClubs(isA(List.class));
        verify(tenantEntity).setEmail(eq("jane.doe@example.org"));
        verify(tenantEntity).setMicronId(eq("42"));
        verify(tenantEntity).setName(eq("No tenants have been added yet"));
        verify(tenantEntity).setPhone(eq("6625550144"));
        verify(tenantEntity).setRemark(eq("No tenants have been added yet"));
        verify(tenantEntity).setTenantId(isA(UUID.class));
        verify(tenantEntity).setUserManagerEntities(isA(List.class));
        verify(tenantEntity).toDomainModel();
        verify(jpaTenantRepository).findAll((Pageable) isNull());
        assertInstanceOf(PageImpl.class, actualFindAllResult);
        List<Tenant> toListResult = actualFindAllResult.toList();
        assertEquals(1, toListResult.size());
        Tenant getResult = toListResult.get(0);
        assertEquals("42", getResult.getMicronId());
        assertEquals("6625550144", getResult.getPhone());
        assertEquals("Cif", getResult.getCif());
        assertEquals("Name", getResult.getName());
        assertEquals("Remark", getResult.getRemark());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertNull(getResult.getAddress());
    }
    @Test
    @DisplayName("Test findAll(Pageable) with 'pageable'; then return toList size is two")
    @MethodsUnderTest({"Page TenantRepositoryAdapter.findAll(Pageable)"})
    void testFindAllWithPageable_thenReturnToListSizeIsTwo() {
        // Arrange
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("No tenants have been added yet");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("No tenants have been added yet");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("No tenants have been added yet");
        UUID tenantId = UUID.randomUUID();
        tenantEntity.setTenantId(tenantId);
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        TenantEntity tenantEntity2 = new TenantEntity();
        tenantEntity2.setAddress("17 High St");
        tenantEntity2.setCif("Cif");
        tenantEntity2.setClubs(new ArrayList<>());
        tenantEntity2.setEmail("john.smith@example.org");
        tenantEntity2.setMicronId("No tenants have been added yet");
        tenantEntity2.setName("Name");
        tenantEntity2.setPhone("8605550118");
        tenantEntity2.setRemark("Remark");
        tenantEntity2.setTenantId(UUID.randomUUID());
        tenantEntity2.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> content = new ArrayList<>();
        content.add(tenantEntity2);
        content.add(tenantEntity);
        when(jpaTenantRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));

        // Act
        Page<Tenant> actualFindAllResult = tenantRepositoryAdapter.findAll(null);

        // Assert
        verify(jpaTenantRepository).findAll((Pageable) isNull());
        assertInstanceOf(PageImpl.class, actualFindAllResult);
        List<Tenant> toListResult = actualFindAllResult.toList();
        assertEquals(2, toListResult.size());
        Tenant getResult = toListResult.get(0);
        assertEquals("17 High St", getResult.getAddress());
        Tenant getResult2 = toListResult.get(1);
        assertEquals("42 Main St", getResult2.getAddress());
        assertEquals("42", getResult2.getMicronId());
        assertEquals("6625550144", getResult2.getPhone());
        assertEquals("8605550118", getResult.getPhone());
        assertEquals("No tenants have been added yet", getResult2.getCif());
        assertEquals("No tenants have been added yet", getResult.getMicronId());
        assertEquals("No tenants have been added yet", getResult2.getName());
        assertEquals("No tenants have been added yet", getResult2.getRemark());
        assertEquals("jane.doe@example.org", getResult2.getEmail());
        assertEquals("john.smith@example.org", getResult.getEmail());
        assertTrue(getResult2.getClubs().isEmpty());
        assertTrue(getResult2.getUserManagers().isEmpty());
        assertSame(tenantId, getResult2.getTenantId());
    }
    @Test
    @DisplayName("Test findAll(String, Pageable) with 'search', 'pageable'; then return toList first Address is '42 Main St'")
    @MethodsUnderTest({"Page TenantRepositoryAdapter.findAll(String, Pageable)"})
    void testFindAllWithSearchPageable_thenReturnToListFirstAddressIs42MainSt() {
        // Arrange
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Tenants not found");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Tenants not found");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Tenants not found");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> content = new ArrayList<>();
        content.add(tenantEntity);
        when(jpaTenantRepository.findAll(Mockito.<Specification<TenantEntity>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(content));

        // Act
        Page<Tenant> actualFindAllResult = tenantRepositoryAdapter.findAll("Search", null);

        // Assert
        verify(jpaTenantRepository).findAll(isA(Specification.class), (Pageable) isNull());
        assertInstanceOf(PageImpl.class, actualFindAllResult);
        List<Tenant> toListResult = actualFindAllResult.toList();
        assertEquals(1, toListResult.size());
        Tenant getResult = toListResult.getFirst();
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals("Tenants not found", getResult.getCif());
        assertEquals("Tenants not found", getResult.getName());
        assertEquals("Tenants not found", getResult.getRemark());
    }

    /**
     * Test {@link TenantRepositoryAdapter#findAll(String, Pageable)} with {@code search}, {@code pageable}.
     * <ul>
     *   <li>Then return toList first MicronId is {@code 42}.</li>
     * </ul>
     * <p>
     * Method under test: {@link TenantRepositoryAdapter#findAll(String, Pageable)}
     */
    @Test
    @DisplayName("Test findAll(String, Pageable) with 'search', 'pageable'; then return toList first MicronId is '42'")
    @MethodsUnderTest({"Page TenantRepositoryAdapter.findAll(String, Pageable)"})
    void testFindAllWithSearchPageable_thenReturnToListFirstMicronIdIs42() {
        // Arrange
        TenantEntity tenantEntity = mock(TenantEntity.class);
        TenantBuilder cifResult = Tenant.builder().cif("Cif");
        TenantBuilder remarkResult = cifResult.clubs(new ArrayList<>())
                .email("jane.doe@example.org")
                .micronId("42")
                .name("Name")
                .phone("6625550144")
                .remark("Remark");
        TenantBuilder tenantIdResult = remarkResult.tenantId(UUID.randomUUID());
        Tenant buildResult = tenantIdResult.userManagers(new ArrayList<>()).build();
        when(tenantEntity.toDomainModel()).thenReturn(buildResult);
        doNothing().when(tenantEntity).setAddress(Mockito.<String>any());
        doNothing().when(tenantEntity).setCif(Mockito.<String>any());
        doNothing().when(tenantEntity).setClubs(Mockito.<List<Club>>any());
        doNothing().when(tenantEntity).setEmail(Mockito.<String>any());
        doNothing().when(tenantEntity).setMicronId(Mockito.<String>any());
        doNothing().when(tenantEntity).setName(Mockito.<String>any());
        doNothing().when(tenantEntity).setPhone(Mockito.<String>any());
        doNothing().when(tenantEntity).setRemark(Mockito.<String>any());
        doNothing().when(tenantEntity).setTenantId(Mockito.<UUID>any());
        doNothing().when(tenantEntity).setUserManagerEntities(Mockito.<List<UserManager>>any());
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Tenants not found");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Tenants not found");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Tenants not found");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> content = new ArrayList<>();
        content.add(tenantEntity);
        when(jpaTenantRepository.findAll(Mockito.<Specification<TenantEntity>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(content));

        // Act
        Page<Tenant> actualFindAllResult = tenantRepositoryAdapter.findAll("Search", null);

        // Assert
        verify(tenantEntity).setAddress(eq("42 Main St"));
        verify(tenantEntity).setCif(eq("Tenants not found"));
        verify(tenantEntity).setClubs(isA(List.class));
        verify(tenantEntity).setEmail(eq("jane.doe@example.org"));
        verify(tenantEntity).setMicronId(eq("42"));
        verify(tenantEntity).setName(eq("Tenants not found"));
        verify(tenantEntity).setPhone(eq("6625550144"));
        verify(tenantEntity).setRemark(eq("Tenants not found"));
        verify(tenantEntity).setTenantId(isA(UUID.class));
        verify(tenantEntity).setUserManagerEntities(isA(List.class));
        verify(tenantEntity).toDomainModel();
        verify(jpaTenantRepository).findAll(isA(Specification.class), (Pageable) isNull());
        assertTrue(actualFindAllResult instanceof PageImpl);
        List<Tenant> toListResult = actualFindAllResult.toList();
        assertEquals(1, toListResult.size());
        Tenant getResult = toListResult.get(0);
        assertEquals("42", getResult.getMicronId());
        assertEquals("6625550144", getResult.getPhone());
        assertEquals("Cif", getResult.getCif());
        assertEquals("Name", getResult.getName());
        assertEquals("Remark", getResult.getRemark());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertNull(getResult.getAddress());
    }
    @Test
    @DisplayName("Test findAll(String, Pageable) with 'search', 'pageable'; then return toList size is two")
    @MethodsUnderTest({"Page TenantRepositoryAdapter.findAll(String, Pageable)"})
    void testFindAllWithSearchPageable_thenReturnToListSizeIsTwo() {
        // Arrange
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Tenants not found");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Tenants not found");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Tenants not found");
        UUID tenantId = UUID.randomUUID();
        tenantEntity.setTenantId(tenantId);
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        TenantEntity tenantEntity2 = new TenantEntity();
        tenantEntity2.setAddress("17 High St");
        tenantEntity2.setCif("Cif");
        tenantEntity2.setClubs(new ArrayList<>());
        tenantEntity2.setEmail("john.smith@example.org");
        tenantEntity2.setMicronId("Tenants not found");
        tenantEntity2.setName("Name");
        tenantEntity2.setPhone("8605550118");
        tenantEntity2.setRemark("Remark");
        tenantEntity2.setTenantId(UUID.randomUUID());
        tenantEntity2.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> content = new ArrayList<>();
        content.add(tenantEntity2);
        content.add(tenantEntity);
        when(jpaTenantRepository.findAll(Mockito.<Specification<TenantEntity>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(content));

        // Act
        Page<Tenant> actualFindAllResult = tenantRepositoryAdapter.findAll("Search", null);

        // Assert
        verify(jpaTenantRepository).findAll(isA(Specification.class), (Pageable) isNull());
        assertInstanceOf(PageImpl.class, actualFindAllResult);
        List<Tenant> toListResult = actualFindAllResult.toList();
        assertEquals(2, toListResult.size());
        Tenant getResult = toListResult.get(0);
        assertEquals("17 High St", getResult.getAddress());
        Tenant getResult2 = toListResult.get(1);
        assertEquals("42 Main St", getResult2.getAddress());
        assertEquals("42", getResult2.getMicronId());
        assertEquals("6625550144", getResult2.getPhone());
        assertEquals("8605550118", getResult.getPhone());
        assertEquals("Tenants not found", getResult2.getCif());
        assertEquals("Tenants not found", getResult.getMicronId());
        assertEquals("Tenants not found", getResult2.getName());
        assertEquals("Tenants not found", getResult2.getRemark());
        assertEquals("jane.doe@example.org", getResult2.getEmail());
        assertEquals("john.smith@example.org", getResult.getEmail());
        assertTrue(getResult2.getClubs().isEmpty());
        assertTrue(getResult2.getUserManagers().isEmpty());
        assertSame(tenantId, getResult2.getTenantId());
    }
    @Test
    @DisplayName("Test findAllTenantsSummary(); given TenantEntity() Address is '17 High St'; then return size is two")
    @MethodsUnderTest({"List TenantRepositoryAdapter.findAllTenantsSummary()"})
    void testFindAllTenantsSummary_givenTenantEntityAddressIs17HighSt_thenReturnSizeIsTwo() {
        // Arrange
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Tenants not found");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Tenants not found");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Tenants not found");
        UUID tenantId = UUID.randomUUID();
        tenantEntity.setTenantId(tenantId);
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        TenantEntity tenantEntity2 = new TenantEntity();
        tenantEntity2.setAddress("17 High St");
        tenantEntity2.setCif("Cif");
        tenantEntity2.setClubs(new ArrayList<>());
        tenantEntity2.setEmail("john.smith@example.org");
        tenantEntity2.setMicronId("Tenants not found");
        tenantEntity2.setName("Name");
        tenantEntity2.setPhone("8605550118");
        tenantEntity2.setRemark("Remark");
        tenantEntity2.setTenantId(UUID.randomUUID());
        tenantEntity2.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> tenantEntityList = new ArrayList<>();
        tenantEntityList.add(tenantEntity2);
        tenantEntityList.add(tenantEntity);
        when(jpaTenantRepository.findAll()).thenReturn(tenantEntityList);

        // Act
        List<Tenant> actualFindAllTenantsSummaryResult = tenantRepositoryAdapter.findAllTenantsSummary();

        // Assert
        verify(jpaTenantRepository).findAll();
        assertEquals(2, actualFindAllTenantsSummaryResult.size());
        Tenant getResult = actualFindAllTenantsSummaryResult.get(0);
        assertEquals("17 High St", getResult.getAddress());
        Tenant getResult2 = actualFindAllTenantsSummaryResult.get(1);
        assertEquals("42 Main St", getResult2.getAddress());
        assertEquals("42", getResult2.getMicronId());
        assertEquals("6625550144", getResult2.getPhone());
        assertEquals("8605550118", getResult.getPhone());
        assertEquals("Tenants not found", getResult2.getCif());
        assertEquals("Tenants not found", getResult.getMicronId());
        assertEquals("Tenants not found", getResult2.getName());
        assertEquals("Tenants not found", getResult2.getRemark());
        assertEquals("jane.doe@example.org", getResult2.getEmail());
        assertEquals("john.smith@example.org", getResult.getEmail());
        assertTrue(getResult2.getClubs().isEmpty());
        assertTrue(getResult2.getUserManagers().isEmpty());
        assertSame(tenantId, getResult2.getTenantId());
    }
    @Test
    @DisplayName("Test findAllTenantsSummary(); then return first Address is '42 Main St'")
    @MethodsUnderTest({"List TenantRepositoryAdapter.findAllTenantsSummary()"})
    void testFindAllTenantsSummary_thenReturnFirstAddressIs42MainSt() {
        // Arrange
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Tenants not found");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Tenants not found");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Tenants not found");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> tenantEntityList = new ArrayList<>();
        tenantEntityList.add(tenantEntity);
        when(jpaTenantRepository.findAll()).thenReturn(tenantEntityList);

        // Act
        List<Tenant> actualFindAllTenantsSummaryResult = tenantRepositoryAdapter.findAllTenantsSummary();

        // Assert
        verify(jpaTenantRepository).findAll();
        assertEquals(1, actualFindAllTenantsSummaryResult.size());
        Tenant getResult = actualFindAllTenantsSummaryResult.get(0);
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals("Tenants not found", getResult.getCif());
        assertEquals("Tenants not found", getResult.getName());
        assertEquals("Tenants not found", getResult.getRemark());
    }
    @Test
    @DisplayName("Test findAllTenantsSummary(); then return first MicronId is '42'")
    @MethodsUnderTest({"List TenantRepositoryAdapter.findAllTenantsSummary()"})
    void testFindAllTenantsSummary_thenReturnFirstMicronIdIs42() {
        // Arrange
        TenantEntity tenantEntity = mock(TenantEntity.class);
        TenantBuilder cifResult = Tenant.builder().cif("Cif");
        TenantBuilder remarkResult = cifResult.clubs(new ArrayList<>())
                .email("jane.doe@example.org")
                .micronId("42")
                .name("Name")
                .phone("6625550144")
                .remark("Remark");
        TenantBuilder tenantIdResult = remarkResult.tenantId(UUID.randomUUID());
        Tenant buildResult = tenantIdResult.userManagers(new ArrayList<>()).build();
        when(tenantEntity.toDomainModel()).thenReturn(buildResult);
        doNothing().when(tenantEntity).setAddress(Mockito.<String>any());
        doNothing().when(tenantEntity).setCif(Mockito.<String>any());
        doNothing().when(tenantEntity).setClubs(Mockito.<List<Club>>any());
        doNothing().when(tenantEntity).setEmail(Mockito.<String>any());
        doNothing().when(tenantEntity).setMicronId(Mockito.<String>any());
        doNothing().when(tenantEntity).setName(Mockito.<String>any());
        doNothing().when(tenantEntity).setPhone(Mockito.<String>any());
        doNothing().when(tenantEntity).setRemark(Mockito.<String>any());
        doNothing().when(tenantEntity).setTenantId(Mockito.<UUID>any());
        doNothing().when(tenantEntity).setUserManagerEntities(Mockito.<List<UserManager>>any());
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Tenants not found");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Tenants not found");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Tenants not found");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());

        ArrayList<TenantEntity> tenantEntityList = new ArrayList<>();
        tenantEntityList.add(tenantEntity);
        when(jpaTenantRepository.findAll()).thenReturn(tenantEntityList);

        // Act
        List<Tenant> actualFindAllTenantsSummaryResult = tenantRepositoryAdapter.findAllTenantsSummary();

        // Assert
        verify(tenantEntity).setAddress(eq("42 Main St"));
        verify(tenantEntity).setCif(eq("Tenants not found"));
        verify(tenantEntity).setClubs(isA(List.class));
        verify(tenantEntity).setEmail(eq("jane.doe@example.org"));
        verify(tenantEntity).setMicronId(eq("42"));
        verify(tenantEntity).setName(eq("Tenants not found"));
        verify(tenantEntity).setPhone(eq("6625550144"));
        verify(tenantEntity).setRemark(eq("Tenants not found"));
        verify(tenantEntity).setTenantId(isA(UUID.class));
        verify(tenantEntity).setUserManagerEntities(isA(List.class));
        verify(tenantEntity).toDomainModel();
        verify(jpaTenantRepository).findAll();
        assertEquals(1, actualFindAllTenantsSummaryResult.size());
        Tenant getResult = actualFindAllTenantsSummaryResult.get(0);
        assertEquals("42", getResult.getMicronId());
        assertEquals("6625550144", getResult.getPhone());
        assertEquals("Cif", getResult.getCif());
        assertEquals("Name", getResult.getName());
        assertEquals("Remark", getResult.getRemark());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertNull(getResult.getAddress());
    }
    @Test
    @DisplayName("Test update(UUID, Tenant); given '42 Main St'; then return Address is '42 Main St'")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.update(UUID, Tenant)"})
    void testUpdate_given42MainSt_thenReturnAddressIs42MainSt() {
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
        when(jpaTenantRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(jpaTenantRepository.save(Mockito.<TenantEntity>any())).thenReturn(tenantEntity);
        when(jpaTenantRepository.existsById(Mockito.<UUID>any())).thenReturn(true);
        UUID tenantId2 = UUID.randomUUID();
        Tenant tenant = mock(Tenant.class);
        when(tenant.getAddress()).thenReturn("42 Main St");
        when(tenant.getCif()).thenReturn("Cif");
        when(tenant.getEmail()).thenReturn("jane.doe@example.org");
        when(tenant.getMicronId()).thenReturn("42");
        when(tenant.getPhone()).thenReturn("6625550144");
        when(tenant.getRemark()).thenReturn("Remark");
        when(tenant.getClubs()).thenReturn(new ArrayList<>());
        when(tenant.getUserManagers()).thenReturn(new ArrayList<>());
        when(tenant.getTenantId()).thenReturn(UUID.randomUUID());
        doNothing().when(tenant).setTenantId(Mockito.<UUID>any());
        when(tenant.getName()).thenReturn("Name");

        // Act
        Tenant actualUpdateResult = tenantRepositoryAdapter.update(tenantId2, tenant);

        // Assert
        verify(tenant).getAddress();
        verify(tenant).getCif();
        verify(tenant).getClubs();
        verify(tenant).getEmail();
        verify(tenant).getMicronId();
        verify(tenant, atLeast(1)).getName();
        verify(tenant).getPhone();
        verify(tenant).getRemark();
        verify(tenant).getTenantId();
        verify(tenant).getUserManagers();
        verify(tenant).setTenantId(isA(UUID.class));
        verify(jpaTenantRepository).existsByName(eq("Name"));
        verify(jpaTenantRepository).existsById(isA(UUID.class));
        verify(jpaTenantRepository).save(isA(TenantEntity.class));
        assertEquals("42 Main St", actualUpdateResult.getAddress());
        assertEquals("42", actualUpdateResult.getMicronId());
        assertEquals("6625550144", actualUpdateResult.getPhone());
        assertEquals("Cif", actualUpdateResult.getCif());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals("Remark", actualUpdateResult.getRemark());
        assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
        assertTrue(actualUpdateResult.getClubs().isEmpty());
        assertTrue(actualUpdateResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualUpdateResult.getTenantId());
    }
    @Test
    @DisplayName("Test update(UUID, Tenant); then return Address is 'null'")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.update(UUID, Tenant)"})
    void testUpdate_thenReturnAddressIsNull() {
        // Arrange
        TenantEntity tenantEntity = mock(TenantEntity.class);
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
        when(tenantEntity.toDomainModel()).thenReturn(buildResult);
        doNothing().when(tenantEntity).setAddress(Mockito.<String>any());
        doNothing().when(tenantEntity).setCif(Mockito.<String>any());
        doNothing().when(tenantEntity).setClubs(Mockito.<List<Club>>any());
        doNothing().when(tenantEntity).setEmail(Mockito.<String>any());
        doNothing().when(tenantEntity).setMicronId(Mockito.<String>any());
        doNothing().when(tenantEntity).setName(Mockito.<String>any());
        doNothing().when(tenantEntity).setPhone(Mockito.<String>any());
        doNothing().when(tenantEntity).setRemark(Mockito.<String>any());
        doNothing().when(tenantEntity).setTenantId(Mockito.<UUID>any());
        doNothing().when(tenantEntity).setUserManagerEntities(Mockito.<List<UserManager>>any());
        tenantEntity.setAddress("42 Main St");
        tenantEntity.setCif("Cif");
        tenantEntity.setClubs(new ArrayList<>());
        tenantEntity.setEmail("jane.doe@example.org");
        tenantEntity.setMicronId("42");
        tenantEntity.setName("Name");
        tenantEntity.setPhone("6625550144");
        tenantEntity.setRemark("Remark");
        tenantEntity.setTenantId(UUID.randomUUID());
        tenantEntity.setUserManagerEntities(new ArrayList<>());
        when(jpaTenantRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(jpaTenantRepository.save(Mockito.<TenantEntity>any())).thenReturn(tenantEntity);
        when(jpaTenantRepository.existsById(Mockito.<UUID>any())).thenReturn(true);
        UUID tenantId2 = UUID.randomUUID();
        Tenant tenant = mock(Tenant.class);
        when(tenant.getAddress()).thenReturn("42 Main St");
        when(tenant.getCif()).thenReturn("Cif");
        when(tenant.getEmail()).thenReturn("jane.doe@example.org");
        when(tenant.getMicronId()).thenReturn("42");
        when(tenant.getPhone()).thenReturn("6625550144");
        when(tenant.getRemark()).thenReturn("Remark");
        when(tenant.getClubs()).thenReturn(new ArrayList<>());
        when(tenant.getUserManagers()).thenReturn(new ArrayList<>());
        when(tenant.getTenantId()).thenReturn(UUID.randomUUID());
        doNothing().when(tenant).setTenantId(Mockito.<UUID>any());
        when(tenant.getName()).thenReturn("Name");

        // Act
        Tenant actualUpdateResult = tenantRepositoryAdapter.update(tenantId2, tenant);

        // Assert
        verify(tenant).getAddress();
        verify(tenant).getCif();
        verify(tenant).getClubs();
        verify(tenant).getEmail();
        verify(tenant).getMicronId();
        verify(tenant, atLeast(1)).getName();
        verify(tenant).getPhone();
        verify(tenant).getRemark();
        verify(tenant).getTenantId();
        verify(tenant).getUserManagers();
        verify(tenant).setTenantId(isA(UUID.class));
        verify(tenantEntity).setAddress(eq("42 Main St"));
        verify(tenantEntity).setCif(eq("Cif"));
        verify(tenantEntity).setClubs(isA(List.class));
        verify(tenantEntity).setEmail(eq("jane.doe@example.org"));
        verify(tenantEntity).setMicronId(eq("42"));
        verify(tenantEntity).setName(eq("Name"));
        verify(tenantEntity).setPhone(eq("6625550144"));
        verify(tenantEntity).setRemark(eq("Remark"));
        verify(tenantEntity).setTenantId(isA(UUID.class));
        verify(tenantEntity).setUserManagerEntities(isA(List.class));
        verify(tenantEntity).toDomainModel();
        verify(jpaTenantRepository).existsByName(eq("Name"));
        verify(jpaTenantRepository).existsById(isA(UUID.class));
        verify(jpaTenantRepository).save(isA(TenantEntity.class));
        assertEquals("42", actualUpdateResult.getMicronId());
        assertEquals("6625550144", actualUpdateResult.getPhone());
        assertEquals("Cif", actualUpdateResult.getCif());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals("Remark", actualUpdateResult.getRemark());
        assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
        assertNull(actualUpdateResult.getAddress());
        assertTrue(actualUpdateResult.getClubs().isEmpty());
        assertTrue(actualUpdateResult.getUserManagers().isEmpty());
        assertSame(tenantId, actualUpdateResult.getTenantId());
    }
    @Test
    @DisplayName("Test update(UUID, Tenant); then Tenant() TenantId is randomUUID")
    @MethodsUnderTest({"Tenant TenantRepositoryAdapter.update(UUID, Tenant)"})
    void testUpdate_thenTenantTenantIdIsRandomUUID() {
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
        when(jpaTenantRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(jpaTenantRepository.save(Mockito.<TenantEntity>any())).thenReturn(tenantEntity);
        when(jpaTenantRepository.existsById(Mockito.<UUID>any())).thenReturn(true);
        UUID tenantId2 = UUID.randomUUID();
        Tenant tenant = new Tenant();

        // Act
        Tenant actualUpdateResult = tenantRepositoryAdapter.update(tenantId2, tenant);

        // Assert
        verify(jpaTenantRepository).existsByName(isNull());
        verify(jpaTenantRepository).existsById(isA(UUID.class));
        verify(jpaTenantRepository).save(isA(TenantEntity.class));
        assertEquals("42 Main St", actualUpdateResult.getAddress());
        assertEquals("42", actualUpdateResult.getMicronId());
        assertEquals("6625550144", actualUpdateResult.getPhone());
        assertEquals("Cif", actualUpdateResult.getCif());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals("Remark", actualUpdateResult.getRemark());
        assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
        assertTrue(actualUpdateResult.getClubs().isEmpty());
        assertTrue(actualUpdateResult.getUserManagers().isEmpty());
        assertSame(tenantId2, tenant.getTenantId());
        assertSame(tenantId, actualUpdateResult.getTenantId());
    }
    @Test
    @DisplayName("Test deleteById(UUID); given JpaTenantRepository existsById(Object) return 'true'; then calls deleteById(Object)")
    @MethodsUnderTest({"void TenantRepositoryAdapter.deleteById(UUID)"})
    void testDeleteById_givenJpaTenantRepositoryExistsByIdReturnTrue_thenCallsDeleteById() {
        // Arrange
        doNothing().when(jpaTenantRepository).deleteById(Mockito.<UUID>any());
        when(jpaTenantRepository.existsById(Mockito.<UUID>any())).thenReturn(true);

        // Act
        tenantRepositoryAdapter.deleteById(UUID.randomUUID());

        // Assert
        verify(jpaTenantRepository).deleteById(isA(UUID.class));
        verify(jpaTenantRepository).existsById(isA(UUID.class));
    }
}
