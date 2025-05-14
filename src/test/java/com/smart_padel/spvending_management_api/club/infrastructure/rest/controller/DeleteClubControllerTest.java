package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;

import com.smart_padel.spvending_management_api.club.domain.ports.in.DeleteClubUseCase;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
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
@WebMvcTest(DeleteClubController.class)
class DeleteClubControllerTest {
    @MockitoBean
    private DeleteClubUseCase deleteClubUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Test
    @DisplayName("should return 204 NO CONTENT when removing an existing club")
    void shouldReturnNoContentWhenDeletingExistingClub() throws Exception {
        UUID clubId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/clubs/" + clubId))
                .andExpect(status().isNoContent());

        Mockito.verify(deleteClubUseCase).deleteClub(clubId);
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST if clubId is invalid")
    void shouldReturnBadRequestForInvalidClubId() throws Exception {
        mockMvc.perform(delete("/api/v1/clubs/invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}
