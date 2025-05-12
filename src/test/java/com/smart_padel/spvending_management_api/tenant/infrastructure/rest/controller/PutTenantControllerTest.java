package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.UpdateTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoIn;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper.TenantMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PutTenantController.class)
class PutTenantControllerTest {
    @MockitoBean
    private UpdateTenantUseCase updateTenantUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Test
    @DisplayName("should update tenant successfully and return 200 OK")
    void shouldUpdateTenantSuccessfully() throws Exception {
        UUID tenantId = UUID.randomUUID();
        TenantDtoIn tenantDtoIn = new TenantDtoIn(
                "updatedName",
                "B12345678",
                "Calle Ejemplo 123",
                "123456789",
                "updatedemail@gmail.com",
                "Descripción válida",
                "987654321"
        );
        Tenant tenant = TenantMapper.toModel(tenantDtoIn);
        tenant.setTenantId(tenantId);
        Mockito.when(updateTenantUseCase.updateTenant(Mockito.eq(tenantId), Mockito.any(Tenant.class))).thenReturn(tenant);
        mockMvc.perform(put("/api/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tenantDtoIn)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenantId").value(tenantId.toString()))
                .andExpect(jsonPath("$.name").value("updatedName"))
                .andExpect(jsonPath("$.remark").value("Descripción válida"))
                .andExpect(jsonPath("$.cif").value("B12345678"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.email").value("updatedemail@gmail.com"))
                .andExpect(jsonPath("$.address").value("Calle Ejemplo 123"))
                .andExpect(jsonPath("$.micronId").value("987654321"));

        Mockito.verify(updateTenantUseCase).updateTenant(Mockito.eq(tenantId), Mockito.any(Tenant.class));
    }
    @Test
    @DisplayName("should return 400 BAD REQUEST when tenant input is invalid")
    void shouldReturnBadRequestForInvalidTenantInput() throws Exception {
        UUID tenantId = UUID.randomUUID();
        TenantDtoIn invalidTenantDtoIn = new TenantDtoIn("", "", "", "", "invalidEmail", "", "");

        mockMvc.perform(put("/api/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidTenantDtoIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 404 NOT FOUND when tenant does not exist")
    void shouldReturnNotFoundWhenTenantDoesNotExist() throws Exception {
        UUID tenantId = UUID.randomUUID();
        TenantDtoIn tenantDtoIn = new TenantDtoIn("name", "1232as", "address", "description", "email@gmail.com", "123456789", "123456789");

        Mockito.when(updateTenantUseCase.updateTenant(Mockito.eq(tenantId), Mockito.any(Tenant.class)))
                .thenThrow(new ResourceNotFoundException("Tenant not found"));

        mockMvc.perform(put("/api/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tenantDtoIn)))
                .andExpect(status().isNotFound());
    }
}
