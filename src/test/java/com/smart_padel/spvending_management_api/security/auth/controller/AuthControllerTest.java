package com.smart_padel.spvending_management_api.security.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.security.auth.dto.AuthRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.RegisterRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.UserResponse;
import com.smart_padel.spvending_management_api.security.auth.service.AuthService;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.security.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should return 200 OK when registration is successful")
    void shouldReturnOkWhenRegistrationIsSuccessful() throws Exception {
        RegisterRequest request = new RegisterRequest("username", "password", Role.ADMIN);
        UserResponse expectedResponse = new UserResponse("username", Role.ADMIN);

        Mockito.when(authService.register(Mockito.any(RegisterRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("username"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when registration request is invalid")
    void shouldReturnBadRequestWhenRegistrationRequestIsInvalid() throws Exception {
        RegisterRequest invalidRequest = new RegisterRequest("", "password", Role.ADMIN);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 200 OK when login is successful")
    void shouldReturnOkWhenLoginIsSuccessful() throws Exception {
        AuthRequest request = new AuthRequest("username", "password");
        UserResponse expectedResponse = new UserResponse("username", Role.ADMIN);

        Mockito.when(authService.authenticate(Mockito.any(AuthRequest.class), Mockito.any(HttpServletResponse.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("username"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when login request is invalid")
    void shouldReturnBadRequestWhenLoginRequestIsInvalid() throws Exception {
        AuthRequest invalidRequest = new AuthRequest("", "password");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 200 OK when refresh token is successful")
    void shouldReturnOkWhenRefreshTokenIsSuccessful() throws Exception {
        UserResponse expectedResponse = new UserResponse("username", Role.ADMIN);

        Mockito.when(authService.refreshToken(Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value("username"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }
}
