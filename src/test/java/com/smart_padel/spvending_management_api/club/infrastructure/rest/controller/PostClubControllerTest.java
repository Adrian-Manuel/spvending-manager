package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.CreateClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoIn;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
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
@WebMvcTest(PostClubController.class)
class PostClubControllerTest {
    @MockitoBean
    private CreateClubUseCase createClubUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Test
    @DisplayName("should create club successfully and return 201 CREATED")

    void shouldCreateClubSuccessfully() throws Exception {
        UUID tenantId= UUID.randomUUID();
        UUID clubId= UUID.randomUUID();
        ClubDtoIn clubDtoIn = new ClubDtoIn("name", "1232as", "address", "1245456","email@gmail.com", "remark","asdsa123",tenantId, "accountId");
        Club club = ClubMapper.toModel(clubDtoIn);
        club.setClubId(clubId);
        Mockito.when(createClubUseCase.createClub(Mockito.eq(tenantId),Mockito.any(Club.class))).thenReturn(club);

        mockMvc.perform(post("/api/v1/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clubDtoIn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clubId").value(clubId.toString()))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.address").value("address"))
                .andExpect(jsonPath("$.phone").value("1245456"));
        Mockito.verify(createClubUseCase).createClub(Mockito.eq(tenantId),Mockito.any(Club.class));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when club input is invalid")
    void shouldReturnBadRequestForInvalidClubInput() throws Exception {
        ClubDtoIn invalidClubDtoIn = new ClubDtoIn("", "", "", "", "invalidEmail", "", "123456789", UUID.randomUUID(), "accountId");
        mockMvc.perform(post("/api/v1/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidClubDtoIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when tenantId is invalid")
    void shouldReturnBadRequestForInvalidTenantId() throws Exception {
        ClubDtoIn clubDtoIn = new ClubDtoIn("name", "1232as", "address", "1245456", "email@gmail.com", "remark", "asdsa123", null, "accountId");
        mockMvc.perform(post("/api/v1/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clubDtoIn)))
                .andExpect(status().isBadRequest());
    }
}
