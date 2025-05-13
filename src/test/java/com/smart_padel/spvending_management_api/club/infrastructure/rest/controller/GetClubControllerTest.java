package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.RetrieveClubUseCase;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.RetrieveMachineUseCase;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
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
@WebMvcTest(GetClubController.class)
class GetClubControllerTest {
    @MockitoBean
    private RetrieveClubUseCase retrieveClubUseCase;
    @MockitoBean
    private RetrieveMachineUseCase retrieveMachineUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;

    @Test
    @DisplayName("should return 200 OK with a paginated list of clubs when search is provided")
    void shouldReturnPaginatedClubsWhenSearchIsProvided() throws Exception {
        String search = "tenantName";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Club> clubs = Page.empty();
        Mockito.when(retrieveClubUseCase.getAllClubs(search, pageable)).thenReturn(clubs);

        mockMvc.perform(get("/api/v1/clubs")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isOk());

        Mockito.verify(retrieveClubUseCase).getAllClubs(search, pageable);
    }

    @Test
    @DisplayName("should return 200 OK with a paginated list of clubs when search is not provided")
    void shouldReturnPaginatedClubsWhenSearchIsNotProvided() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Club> clubs = Page.empty();
        Mockito.when(retrieveClubUseCase.getAllClubs(null,pageable)).thenReturn(clubs);

        mockMvc.perform(get("/api/v1/clubs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when page or size parameters are invalid")
    void shouldReturnBadRequestForInvalidPageOrSizeParameters() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                        .param("page", "-1")
                        .param("size", "0")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 200 OK when club is retrieved by ID successfully")
    void shouldGetClubByIdSuccessfully() throws Exception {
        UUID clubId = UUID.randomUUID();
        Club club = new Club();
        club.setClubId(clubId);
        Mockito.when(retrieveClubUseCase.getClubById(clubId)).thenReturn(club);

        mockMvc.perform(get("/api/v1/clubs/" + clubId))
                .andExpect(status().isOk());
        Mockito.verify(retrieveClubUseCase).getClubById(clubId);
    }

    @Test
    @DisplayName("should return 404 NOT FOUND when club ID does not exist")
    void shouldReturnNotFoundForNotExistentClubId() throws Exception {
        UUID clubId = UUID.randomUUID();
        Mockito.when(retrieveClubUseCase.getClubById(clubId)).thenThrow(new ResourceNotFoundException("There is no club with that Id"));

        mockMvc.perform(get("/api/v1/clubs/" + clubId))
                .andExpect(status().isNotFound());

        Mockito.verify(retrieveClubUseCase).getClubById(clubId);
    }

    @Test
    @DisplayName("should return a list of machines associated with a club")
    void shouldGetMachinesByClubIdSuccessfully() throws Exception {
        UUID clubId = UUID.randomUUID();
        Mockito.when(retrieveMachineUseCase.findAllMachinesByClubId(clubId))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/clubs/" + clubId + "/machines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        Mockito.verify(retrieveMachineUseCase).findAllMachinesByClubId(clubId);
    }

    @Test
    @DisplayName("should return a summary of all clubs")
    void shouldReturnClubsSummary() throws Exception {
        Mockito.when(retrieveClubUseCase.getAllClubsSummary()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/clubs/all-summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        Mockito.verify(retrieveClubUseCase).getAllClubsSummary();
    }

    @Test
    @DisplayName("should return 400 if clubId is not a valid UUID")
    void shouldReturnBadRequestForInvalidClubId() throws Exception {
        mockMvc.perform(get("/api/v1/clubs/invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}
