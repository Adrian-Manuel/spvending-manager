package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.CreateTenantUseCase;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PostTenantController.class)
class PostTenantControllerTest {
    @MockitoBean
    private CreateTenantUseCase createTenantUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Test
    @DisplayName("should create tenant successfully and return 201 CREATED")
    void shouldCreateTenantSuccessfully() throws Exception {
        UUID tenantId= UUID.randomUUID();
        TenantDtoIn tenantDtoIn = new TenantDtoIn("name", "1232as", "address", "12345","adrian@gmail.com", "123456789", "123456789");
        Tenant tenant = TenantMapper.toModel(tenantDtoIn);
        tenant.setTenantId(tenantId);
        Mockito.when(createTenantUseCase.createTenant(Mockito.any(Tenant.class))).thenReturn(tenant);


        mockMvc.perform(post("/api/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tenantDtoIn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tenantId").value(tenantId.toString()))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.cif").value("1232as"))
                .andExpect(jsonPath("$.phone").value("12345"))
                .andExpect(jsonPath("$.email").value("adrian@gmail.com"));
        Mockito.verify(createTenantUseCase).createTenant(Mockito.any(Tenant.class));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when tenant name already exists")
    void shouldReturnBadRequestWhenTenantNameExists() throws Exception {
        TenantDtoIn tenantDtoIn = new TenantDtoIn("name", "1232as", "address", "12345", "example@gmail.com", "asdas", "asdasdda");

        Mockito.when(createTenantUseCase.createTenant(Mockito.any(Tenant.class))).thenThrow(new ResourceAlreadyExistsException("Tenant name already exists"));

        mockMvc.perform(post("/api/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tenantDtoIn)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.Error").value("Tenant name already exists"));
    }
    @Test
    @DisplayName("should return 400 BAD REQUEST when tenant input is invalid")
    void shouldReturnBadRequestForInvalidTenantInput() throws Exception {

        TenantDtoIn invalidTenantDtoIn = new TenantDtoIn("", "", "", "", "invalidEmail", "", "");

        mockMvc.perform(post("/api/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidTenantDtoIn)))
                .andExpect(status().isBadRequest());
    }
}
