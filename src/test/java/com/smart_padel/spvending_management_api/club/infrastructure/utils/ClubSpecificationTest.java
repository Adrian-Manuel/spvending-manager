package com.smart_padel.spvending_management_api.club.infrastructure.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ClubSpecificationTest {
    @Test
    void constructor_ThrowsException_WhenInvoked() {
        assertThatThrownBy(ClubSpecification::new)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Util class");
    }
}
