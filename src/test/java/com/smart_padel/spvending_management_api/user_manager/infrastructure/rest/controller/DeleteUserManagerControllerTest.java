package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.DeleteUserManagerUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DeleteUserManagerController.class)
class DeleteUserManagerControllerTest {
    @MockitoBean
    private DeleteUserManagerUseCase deleteUserManagerUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Test
    @DisplayName("should delete user manager successfully and return 204 NO CONTENT")
    void shouldDeleteUserManagerSuccessfully() throws Exception {
        // Given
        UUID userId= UUID.randomUUID();

        // When
        mockMvc.perform(delete("/api/v1/user-managers/{userId}", userId))
                .andExpect(status().isNoContent());

        // Then
        Mockito.verify(deleteUserManagerUseCase).deleteUserManager(userId);
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when userId is invalid")
    void shouldReturnBadRequestWhenUserIdIsInvalid() throws Exception {
        // Given
        String invalidUserId = "invalid-uuid";

        // When
        mockMvc.perform(delete("/api/v1/user-managers/{userId}", invalidUserId))
                .andExpect(status().isBadRequest());

        // Then
        Mockito.verify(deleteUserManagerUseCase, Mockito.never()).deleteUserManager(Mockito.any());
    }
}
