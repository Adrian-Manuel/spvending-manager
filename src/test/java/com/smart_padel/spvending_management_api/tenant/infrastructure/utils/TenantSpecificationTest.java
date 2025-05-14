package com.smart_padel.spvending_management_api.tenant.infrastructure.utils;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TenantSpecificationTest {
    @Test
    void constructor_ThrowsException_WhenInvoked() {
        assertThatThrownBy(TenantSpecification::new)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Util class");
    }
}
