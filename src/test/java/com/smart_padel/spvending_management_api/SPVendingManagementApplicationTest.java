package com.smart_padel.spvending_management_api;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class SPVendingManagementApplicationTest {
    @Test
    @DisplayName("applicationContextLoadsSuccessfully should start the Spring application without errors")
    void applicationContextLoadsSuccessfully() {
        assertDoesNotThrow(() -> SPVendingManagementApplication.main(new String[]{}));
    }

}
