package com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity;

import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserManagerEntityTest {
    @Test
    @DisplayName("fromDomainModel should map UserManager to UserManagerEntity correctly")
    void fromDomainModelMapsCorrectly() {
        UserManager userManager = UserManager.builder()
                .userId(UUID.randomUUID())
                .userName("testUser")
                .password("testPassword")
                .micronId("testMicronId")
                .micronUser("testMicronUser")
                .micronPass("testMicronPass")
                .userType("admin")
                .build();

        UserManagerEntity entity = UserManagerEntity.fromDomainModel(userManager);

        assertThat(entity.getUserId()).isEqualTo(userManager.getUserId());
        assertThat(entity.getUserName()).isEqualTo(userManager.getUserName());
        assertThat(entity.getPassword()).isEqualTo(userManager.getPassword());
        assertThat(entity.getMicronId()).isEqualTo(userManager.getMicronId());
        assertThat(entity.getMicronUser()).isEqualTo(userManager.getMicronUser());
        assertThat(entity.getMicronPass()).isEqualTo(userManager.getMicronPass());
        assertThat(entity.getUserType()).isEqualTo(userManager.getUserType());
    }

    @Test
    @DisplayName("toDomainModel should map UserManagerEntity to UserManager correctly")
    void toDomainModelMapsCorrectly() {
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setName("testTenant");
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setName("testClub");

        UserManagerEntity entity = UserManagerEntity.builder()
                .userId(UUID.randomUUID())
                .userName("testUser")
                .password("testPassword")
                .micronId("testMicronId")
                .micronUser("testMicronUser")
                .micronPass("testMicronPass")
                .userType("admin")
                .tenantEntity(tenantEntity)
                .clubEntity(clubEntity)
                .build();

        UserManager userManager = entity.toDomainModel();

        assertThat(userManager.getUserId()).isEqualTo(entity.getUserId());
        assertThat(userManager.getUserName()).isEqualTo(entity.getUserName());
        assertThat(userManager.getPassword()).isEqualTo(entity.getPassword());
        assertThat(userManager.getMicronId()).isEqualTo(entity.getMicronId());
        assertThat(userManager.getMicronUser()).isEqualTo(entity.getMicronUser());
        assertThat(userManager.getMicronPass()).isEqualTo(entity.getMicronPass());
        assertThat(userManager.getUserType()).isEqualTo(entity.getUserType());
        assertThat(userManager.getTenantName()).isEqualTo(tenantEntity.getName());
        assertThat(userManager.getClubName()).isEqualTo(clubEntity.getName());
    }
    @Test
    @DisplayName("toDomainModel should handle null tenantEntity and clubEntity gracefully")
    void toDomainModelHandlesNullEntities() {
        UserManagerEntity entity = UserManagerEntity.builder()
                .userId(UUID.randomUUID())
                .userName("testUser")
                .password("testPassword")
                .micronId("testMicronId")
                .micronUser("testMicronUser")
                .micronPass("testMicronPass")
                .userType("admin")
                .tenantEntity(null)
                .clubEntity(null)
                .build();

        UserManager userManager = entity.toDomainModel();

        assertThat(userManager.getTenantName()).isNull();
        assertThat(userManager.getClubName()).isNull();
    }
}
