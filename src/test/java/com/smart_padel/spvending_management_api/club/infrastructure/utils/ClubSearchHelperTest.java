package com.smart_padel.spvending_management_api.club.infrastructure.utils;

import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClubSearchHelperTest {

    @Test
    void testBuildClubSearchSpec_WithSearch() {
        UUID tenantId = UUID.randomUUID();
        String search = "name:padel";

        Specification<ClubEntity> spec = ClubSearchHelper.buildClubSearchSpec(tenantId, search);

        assertNotNull(spec);
        // No lanzará nada y construirá una especificación compuesta
    }

    @Test
    void testBuildClubSearchSpec_WithoutSearch() {
        UUID tenantId = UUID.randomUUID();
        String search = "  "; // en blanco

        Specification<ClubEntity> spec = ClubSearchHelper.buildClubSearchSpec(tenantId, search);

        assertNotNull(spec);
        // Especificación sólo por tenant
    }

    @Test
    void testPrivateConstructorThrowsException() {
        assertThrows(IllegalStateException.class, ClubSearchHelper::new);
    }
}
