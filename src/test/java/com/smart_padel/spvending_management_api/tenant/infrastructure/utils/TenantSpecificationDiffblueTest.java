package com.smart_padel.spvending_management_api.tenant.infrastructure.utils;

import static org.junit.jupiter.api.Assertions.assertNull;

import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

class TenantSpecificationDiffblueTest {

    @Test
    @DisplayName("Test withFilter(String); when empty string; then return 'null'")
    @MethodsUnderTest({"Specification TenantSpecification.withFilter(String)"})
    void testWithFilter_whenEmptyString_thenReturnNull() {
        // Arrange and Act
        Specification<TenantEntity> actualWithFilterResult = TenantSpecification.withFilter("");

        // Assert
        assertNull(actualWithFilterResult);
    }
    @Test
    @DisplayName("Test withFilter(String); when 'null'; then return 'null'")
    @MethodsUnderTest({"Specification TenantSpecification.withFilter(String)"})
    void testWithFilter_whenNull_thenReturnNull() {
        // Arrange and Act
        Specification<TenantEntity> actualWithFilterResult = TenantSpecification.withFilter(null);

        // Assert
        assertNull(null);
    }
}
