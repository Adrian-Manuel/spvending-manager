package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.ports.in.RetrieveClubUseCase;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.RetrieveTenantUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(GetTenantController.class)
class GetTenantControllerTest {
    @MockitoBean
    private RetrieveTenantUseCase retrieveTenantUseCase;
    @MockitoBean
    private RetrieveClubUseCase retrieveClubUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;
    @Test
    @DisplayName("should return 200 OK with a paginated list of tenants when search is provided")
    void shouldReturnPaginatedTenantsWhenSearchIsProvided() throws Exception {
        String search = "tenantName";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Tenant> tenants = Page.empty();
        Mockito.when(retrieveTenantUseCase.getAllTenants(search, pageable)).thenReturn(tenants);

        mockMvc.perform(get("/api/v1/tenants")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isOk());

        Mockito.verify(retrieveTenantUseCase).getAllTenants(search, pageable);
    }
    @Test
    @DisplayName("should return 200 OK with a paginated list of tenants when search is not provided")
    void shouldReturnPaginatedTenantsWhenSearchIsNotProvided() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Tenant> tenants = Page.empty();
        Mockito.when(retrieveTenantUseCase.getAllTenants(pageable)).thenReturn(tenants);

        mockMvc.perform(get("/api/v1/tenants")
                        .param("page", "0")
                        .param("size", "10")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isOk());

        Mockito.verify(retrieveTenantUseCase).getAllTenants(pageable);
    }
    @Test
    @DisplayName("should return 400 BAD REQUEST when page or size parameters are invalid")
    void shouldReturnBadRequestForInvalidPageOrSizeParameters() throws Exception {
        mockMvc.perform(get("/api/v1/tenants")
                        .param("page", "-1")
                        .param("size", "0")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("should return 200 OK when tenant is retrieved by ID successfully")
    void shouldGetTenantByIdSuccessfully() throws Exception {
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant();
        Mockito.when(retrieveTenantUseCase.getTenantById(tenantId)).thenReturn(tenant);

        mockMvc.perform(get("/api/v1/tenants/{tenantId}", tenantId)
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isOk());

        Mockito.verify(retrieveTenantUseCase).getTenantById(tenantId);
    }

    @Test
    @DisplayName("should return 404 NOT FOUND when tenant ID does not exist")
    void shouldReturnNotFoundForNonExistentTenantId() throws Exception {
        UUID tenantId = UUID.randomUUID();
        Mockito.when(retrieveTenantUseCase.getTenantById(tenantId)).thenThrow(new ResourceNotFoundException("Tenant not found"));

        mockMvc.perform(get("/api/v1/tenants/{tenantId}", tenantId)
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isNotFound());

        Mockito.verify(retrieveTenantUseCase).getTenantById(tenantId);
    }

    @Test
    @DisplayName("should return 200 OK when clubs are retrieved by tenant ID successfully")
    void shouldGetClubsByTenantIdSuccessfully() throws Exception {
        UUID tenantId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(retrieveClubUseCase.getAllClubsByTenantId(null, tenantId, pageable)).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/tenants/{tenantId}/clubs", tenantId)
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isOk());

        Mockito.verify(retrieveClubUseCase).getAllClubsByTenantId(null, tenantId, pageable);
    }

    @Test
    @DisplayName("should return 200 OK when tenants summary is retrieved successfully")
    void shouldGetTenantsSummarySuccessfully() throws Exception {
        Mockito.when(retrieveTenantUseCase.getAllTenantsSummary()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/tenants/all-summary")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        Mockito.verify(retrieveTenantUseCase).getAllTenantsSummary();
    }

    @Test
    @DisplayName("should return 400 if tenantId is not a valid UUID")
    void shouldReturnBadRequestForInvalidTenantId() throws Exception {
        mockMvc.perform(get("/api/v1/tenants/invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}
