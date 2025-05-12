package com.smart_padel.spvending_management_api.shared.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegexUtilsTest {
    @Test
    void shouldThrowIllegalStateExceptionWhenUtilityClassIsInstantiated() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, RegexUtils::new);
        assertEquals("Utility class", exception.getMessage());
    }
}
