package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import org.springframework.data.domain.Pageable;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.RetrieveUserManagerUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(GetUserManagerController.class)
class GetUserManagerControllerTest {
    @MockitoBean
    private RetrieveUserManagerUseCase retrieveUserManagerUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Value("${app.AESecret_key}")
    private String aeSecretKey;

    @Test
    @DisplayName("should return 200 OK with a paginated list of user managers when search is provided")
    void shouldReturnPaginatedUserManagersWhenSearchIsProvided() throws Exception {
        String search = "machineName";
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserManager> userManagers = Page.empty();
        Mockito.when(retrieveUserManagerUseCase.getAllUserManager(search, pageable)).thenReturn(userManagers);

        mockMvc.perform(get("/api/v1/user-managers")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return 200 OK with a paginated list of user managers when search is not provided")
    void shouldReturnPaginatedUserManagersWhenSearchIsNotProvided() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserManager> userManagers = Page.empty();
        Mockito.when(retrieveUserManagerUseCase.getAllUserManager(null,pageable)).thenReturn(userManagers);

        mockMvc.perform(get("/api/v1/user-managers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("should return 200 OK when UserManager is retrieved by ID successfully")
    void shouldGetUserManagerByIdSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn("userName", "userPassword", "userMicronId", "micronUser", "micronPass", "1", tenantId, null);
        UserManager userManager= UserManagerMapper.toModel(userManagerDtoIn, aeSecretKey);
        userManager.setUserId(userId);
        userManager.setTenantName("tenantName");
        Mockito.when(retrieveUserManagerUseCase.getUserManagerById(userId)).thenReturn(userManager);

        mockMvc.perform(get("/api/v1/user-managers/" + userId))
                .andExpect(status().isOk());
        Mockito.verify(retrieveUserManagerUseCase).getUserManagerById(userId);
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when page or size parameters are invalid")
    void shouldReturnBadRequestForInvalidPageOrSizeParameters() throws Exception {
        mockMvc.perform(get("/api/v1/user-managers")
                        .param("page", "-1")
                        .param("size", "0")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 404 NOT FOUND when user manager ID does not exist")
    void shouldReturnNotFoundForNotExistentUserManagerId() throws Exception {
        UUID userManagerId = UUID.randomUUID();
        Mockito.when(retrieveUserManagerUseCase.getUserManagerById(userManagerId)).thenThrow(new ResourceNotFoundException("There is no user manager with that Id"));

        mockMvc.perform(get("/api/v1/user-managers/" + userManagerId))
                .andExpect(status().isNotFound());

        Mockito.verify(retrieveUserManagerUseCase).getUserManagerById(userManagerId);
    }

    @Test
    @DisplayName("should return 400 if clubId is not a valid UUID")
    void shouldReturnBadRequestForInvalidUserManagerId() throws Exception {
        mockMvc.perform(get("/api/v1/user-managers/invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}
