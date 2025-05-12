package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ParamRequiredException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.CreateUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@WebMvcTest(PostUserManagerController.class)
class PostUserManagerControllerTest {
    @MockitoBean
    private CreateUserManagerUseCase createUserManagerUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;

    @Test
    @DisplayName("should create user manager successfully when userType is 2 and return 201 CREATED")
    void shouldCreateUserManagerSuccessfullyWhenUserTypeIs2() throws Exception {
        UUID userManagerId = UUID.randomUUID();
        UUID clubId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn(
                "userName",
                 "Abc1234.",
                "abc123.",
                "asdsa",
                "Abc123.",
                "2",
                null,
                clubId);
        UserManager userManager = UserManagerMapper.toModel(userManagerDtoIn, aeSecretKey);
        userManager.setUserId(userManagerId);
        userManager.setClubName("clubEntityName");

         Mockito.when(createUserManagerUseCase.createUserManager(Mockito.isNull(), Mockito.eq(clubId),Mockito.any(UserManager.class))).thenReturn(userManager);
         mockMvc.perform(post("/api/v1/user-managers")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(new ObjectMapper().writeValueAsString(userManagerDtoIn)))
                .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.userManagerId").value(userManagerId.toString()))
                 .andExpect(jsonPath("$.username").value("userName"))
                 .andExpect(jsonPath("$.micronId").value("abc123."))
                 .andExpect(jsonPath("$.micronUser").value("asdsa"))
                 .andExpect(jsonPath("$.clubEntityName").value("clubEntityName"));

        Mockito.verify(createUserManagerUseCase).createUserManager(Mockito.isNull(), Mockito.eq(clubId),Mockito.any(UserManager.class));

    }

    @Test
    @DisplayName("should create user manager successfully when userType is 1 and return 201 CREATED")
    void shouldCreateUserManagerSuccessfullyWhenUserTypeIs1() throws Exception {
        UUID userManagerId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn(
                "userName",
                "Abc1234.",
                "abc123.",
                "asdsa",
                "Abc123.",
                "1",
                tenantId,
                null);
        UserManager userManager = UserManagerMapper.toModel(userManagerDtoIn, aeSecretKey);
        userManager.setUserId(userManagerId);
        userManager.setTenantName("tenantEntityName");

        Mockito.when(createUserManagerUseCase.createUserManager(Mockito.eq(tenantId), Mockito.isNull(),Mockito.any(UserManager.class))).thenReturn(userManager);
        mockMvc.perform(post("/api/v1/user-managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userManagerDtoIn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userManagerId").value(userManagerId.toString()))
                .andExpect(jsonPath("$.username").value("userName"))
                .andExpect(jsonPath("$.micronId").value("abc123."))
                .andExpect(jsonPath("$.micronUser").value("asdsa"))
                .andExpect(jsonPath("$.tenantEntityName").value("tenantEntityName"));

        Mockito.verify(createUserManagerUseCase).createUserManager(Mockito.eq(tenantId), Mockito.isNull(),Mockito.any(UserManager.class));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when input data is invalid")
    void shouldReturnBadRequestWhenInputDataIsInvalid() throws Exception {
        UserManagerDtoIn invalidUserManagerDtoIn = new UserManagerDtoIn(
                "", // Invalid username
                "1232as",
                "abc123.",
                "asdsa",
                "123432",
                "1",
                null,
                UUID.randomUUID());

        mockMvc.perform(post("/api/v1/user-managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUserManagerDtoIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 409 CONFLICT when username already exists")
    void shouldReturnConflictWhenUsernameAlreadyExists() throws Exception {
        UUID clubId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn(
                "existingUser",
                "Abc1234.",
                "abc123.",
                "asdsa",
                "Abc123.",
                "2",
                null,
                clubId);

        Mockito.when(createUserManagerUseCase.createUserManager(Mockito.isNull(), Mockito.eq(clubId), Mockito.any(UserManager.class)))
                .thenThrow(new ResourceAlreadyExistsException("Username already exists"));

        mockMvc.perform(post("/api/v1/user-managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userManagerDtoIn)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.Error").value("Username already exists"));

    }

    @Test
    @DisplayName("should return 404 NOT FOUND when clubEntityId does not exist")
    void shouldReturnNotFoundWhenClubEntityIdDoesNotExist() throws Exception {
        UUID clubId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn(
                "userName",
                "Abc1234.",
                "abc123.",
                "asdsa",
                "Abc123.",
                "2",
                null,
                clubId);

        Mockito.when(createUserManagerUseCase.createUserManager(Mockito.isNull(), Mockito.eq(clubId), Mockito.any(UserManager.class)))
                .thenThrow(new ResourceNotFoundException("Club not found"));

        mockMvc.perform(post("/api/v1/user-managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userManagerDtoIn)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Error").value("Club not found"));
    }

    @Test
    @DisplayName("should return 404 NOT FOUND when tenantEntityId does not exist")
    void shouldReturnNotFoundWhenTenantEntityIdDoesNotExist() throws Exception {
        UUID tenantId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn(
                "userName",
                "Abc1234.",
                "abc123.",
                "asdsa",
                "Abc123.",
                "1",
                tenantId,
                null);

        Mockito.when(createUserManagerUseCase.createUserManager(Mockito.eq(tenantId), Mockito.isNull(), Mockito.any(UserManager.class)))
                .thenThrow(new ResourceNotFoundException("Tenant not found"));

        mockMvc.perform(post("/api/v1/user-managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userManagerDtoIn)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Error").value("Tenant not found"));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when userType is 2 but tenantEntityId is provided instead of clubEntityId")
    void shouldReturnBadRequestWhenUserTypeIs2ButTenantEntityIdIsProvided() throws Exception {
        UUID tenantId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn(
                "userName",
                "Abc1234.",
                "abc123.",
                "asdsa",
                "Abc123.",
                "2",
                tenantId,
                null);

        Mockito.doThrow(new ParamRequiredException("The user is type 2, the tenant id is required."))
                .when(createUserManagerUseCase)
                .createUserManager(Mockito.eq(tenantId), Mockito.isNull(), Mockito.any(UserManager.class));

        mockMvc.perform(post("/api/v1/user-managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userManagerDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Error").value("The user is type 2, the tenant id is required."));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when userType is 1 but clubEntityId is provided instead of tenantEntityId")
    void shouldReturnBadRequestWhenUserTypeIs1ButClubEntityIdIsProvided() throws Exception {
        UUID clubId = UUID.randomUUID();
        UserManagerDtoIn userManagerDtoIn = new UserManagerDtoIn(
                "userName",
                "Abc1234.",
                "abc123.",
                "asdsa",
                "Abc123.",
                "1", // userType 1 requires tenantEntityId, not clubEntityId
                null,
                clubId);

        Mockito.doThrow(new ParamRequiredException("The user is type 1, the club id is required."))
                .when(createUserManagerUseCase)
                .createUserManager(Mockito.isNull(), Mockito.eq(clubId), Mockito.any(UserManager.class));

        mockMvc.perform(post("/api/v1/user-managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userManagerDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Error").value("The user is type 1, the club id is required."));
    }

}
