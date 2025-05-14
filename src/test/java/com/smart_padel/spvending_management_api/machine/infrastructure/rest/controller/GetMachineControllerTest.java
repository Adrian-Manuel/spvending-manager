package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.RetrieveMachineUseCase;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.shared.utils.AESGCMEncryption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(GetMachineController.class)
class GetMachineControllerTest {
    @MockitoBean
    private RetrieveMachineUseCase retrieveMachineUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;
    @Test
    @DisplayName("should return 200 OK with a paginated list of machines when search is provided")
    void shouldReturnPaginatedMachinesWhenSearchIsProvided() throws Exception {
        String search = "machineName";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Machine> machines = Page.empty();
        Mockito.when(retrieveMachineUseCase.getAllMachines(search, pageable)).thenReturn(machines);

        mockMvc.perform(get("/api/v1/machines")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isOk());
        Mockito.verify(retrieveMachineUseCase).getAllMachines(search, pageable);
    }

    @Test
    @DisplayName("should return 200 OK with a paginated list of machines when search is not provided")
    void shouldReturnPaginatedMachinesWhenSearchIsNotProvided() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Machine> machines = Page.empty();
        Mockito.when(retrieveMachineUseCase.getAllMachines(null,pageable)).thenReturn(machines);

        mockMvc.perform(get("/api/v1/machines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when page or size parameters are invalid")
    void shouldReturnBadRequestForInvalidPageOrSizeParameters() throws Exception {
        mockMvc.perform(get("/api/v1/machines")
                        .param("page", "-1")
                        .param("size", "0")
                        .header(HttpHeaders.ORIGIN, "*"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 200 OK when club is retrieved by ID successfully")
    void shouldGetClubByIdSuccessfully() throws Exception {
        UUID machineId = UUID.randomUUID();

        Machine machine = new Machine();
        machine.setMachineId(machineId);
        machine.setRustdeskPass(AESGCMEncryption.encrypt("abc123.",aeSecretKey));
        machine.setSmartFridgePassword(AESGCMEncryption.encrypt("abc123.",aeSecretKey));
        Mockito.when(retrieveMachineUseCase.getMachineById(machineId)).thenReturn(machine);

        mockMvc.perform(get("/api/v1/machines/" + machineId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineId").value(machineId.toString()));
        Mockito.verify(retrieveMachineUseCase).getMachineById(machineId);

    }

    @Test
    @DisplayName("should return 404 NOT FOUND when machine ID does not exist")
    void shouldReturnNotFoundForNotExistentMachineId() throws Exception {
        UUID machineId = UUID.randomUUID();
        Mockito.when(retrieveMachineUseCase.getMachineById(machineId)).thenThrow(new ResourceNotFoundException("There is no machine with that Id"));

        mockMvc.perform(get("/api/v1/machines/" + machineId))
                .andExpect(status().isNotFound());

        Mockito.verify(retrieveMachineUseCase).getMachineById(machineId);
    }

    @Test
    @DisplayName("should return 400 if clubId is not a valid UUID")
    void shouldReturnBadRequestForInvalidMachineId() throws Exception {
        mockMvc.perform(get("/api/v1/machines/invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}
