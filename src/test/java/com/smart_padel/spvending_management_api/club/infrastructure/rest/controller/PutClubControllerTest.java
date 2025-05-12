package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.UpdateClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoIn;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
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
@WebMvcTest(PutClubController.class)
class PutClubControllerTest {
    @MockitoBean
    private UpdateClubUseCase updateClubUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Test
    @DisplayName("should update club successfully and return 200 OK")
    void shouldUpdateClubSuccessfully() throws Exception{
        UUID clubId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();
        ClubDtoIn clubDtoIn = new ClubDtoIn(
                "updatedName",
                "1232as",
                "updatedAddress",
                "1245456",
                "example@gmail.com",
                "updatedRemark",
                "asdsa123",
                tenantId,
                "accountId"
        );
        Club club = ClubMapper.toModel(clubDtoIn);
        club.setClubId(clubId);
        Mockito.when(updateClubUseCase.updateClub(Mockito.eq(tenantId), Mockito.eq(clubId), Mockito.any(Club.class))).thenReturn(club);
        mockMvc.perform(put("/api/v1/clubs/" + clubId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clubDtoIn)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clubId").value(clubId.toString()))
                .andExpect(jsonPath("$.name").value(club.getName()))
                .andExpect(jsonPath("$.cif").value(club.getCif()))
                .andExpect(jsonPath("$.address").value(club.getAddress()))
                .andExpect(jsonPath("$.phone").value(club.getPhone()))
                .andExpect(jsonPath("$.email").value(club.getEmail()))
                .andExpect(jsonPath("$.remark").value(club.getRemark()))
                .andExpect(jsonPath("$.accountingId").value(club.getAccountingId()));
        Mockito.verify(updateClubUseCase).updateClub(Mockito.eq(tenantId), Mockito.eq(clubId), Mockito.any(Club.class));

    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when club input is invalid")
    void shouldReturnBadRequestForInvalidClubInput() throws Exception {
        UUID clubId = UUID.randomUUID();
        ClubDtoIn invalidClubDtoIn = new ClubDtoIn("", "", "", "", "invalidEmail", "", "", null, null);

        mockMvc.perform(put("/api/v1/clubs/" + clubId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidClubDtoIn)))
                .andExpect(status().isBadRequest());
        Mockito.verifyNoInteractions(updateClubUseCase);
    }

    @Test
    @DisplayName("should return 404 NOT FOUND when club does not exist")
    void shouldReturnNotFoundWhenClubDoesNotExist() throws Exception {
        UUID clubId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();
        ClubDtoIn clubDtoIn = new ClubDtoIn(
                "updatedName",
                "1232as",
                "updatedAddress",
                "1245456",
                "example@gmail.com",
                "updatedRemark",
                "asdsa123",
                tenantId,
                "accountId"
        );

        Mockito.when(updateClubUseCase.updateClub(Mockito.eq(tenantId), Mockito.eq(clubId), Mockito.any(Club.class)))
                .thenThrow(new ResourceNotFoundException("Club not found"));
        mockMvc.perform(put("/api/v1/clubs/" + clubId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clubDtoIn)))
                .andExpect(status().isNotFound());
    }
}
