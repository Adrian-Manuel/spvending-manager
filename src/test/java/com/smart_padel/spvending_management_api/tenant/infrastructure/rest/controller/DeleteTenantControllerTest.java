package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.DeleteTenantUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DeleteTenantController.class)
class DeleteTenantControllerTest {
    @MockitoBean
    private DeleteTenantUseCase deleteTenantUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;


    @Test
    @DisplayName("should delete tenant successfully and return 204 NO CONTENT")
    void shouldDeleteTenantSuccessfully() throws Exception {
        // Given
        UUID tenantId = UUID.randomUUID();

        // When
        Mockito.doNothing().when(deleteTenantUseCase).deleteTenant(tenantId);
        mockMvc.perform(delete("/api/v1/tenants/{tenantId}", tenantId)
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isNoContent());

        // Then
        Mockito.verify(deleteTenantUseCase).deleteTenant(tenantId);
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when tenantId is invalid")
    void shouldReturnBadRequestWhenTenantIdIsInvalid() throws Exception {
        // Given
        String invalidTenantId = "invalid-uuid";

        // When
        mockMvc.perform(delete("/api/v1/tenants/{tenantId}", invalidTenantId)
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isBadRequest());

        // Then
        Mockito.verify(deleteTenantUseCase, Mockito.never()).deleteTenant(Mockito.any());
    }
}