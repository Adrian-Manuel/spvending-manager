package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.UpdateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PutMachineController.class)
class PutMachineControllerTest {
    @MockitoBean
    private UpdateMachineUseCase updateMachineUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;
    @Test
    @DisplayName("should update machine successfully and return 200 OK")
    void shouldUpdateMachineSuccessfully() throws Exception {
        UUID machineId = UUID.randomUUID();
        UUID clubId = UUID.randomUUID();
        MachineDtoIn machineDtoIn = new MachineDtoIn(
                "machineCode",
                "1232as",
                "abc123",
                "asdsa",
                "123432",
                "asdd12",
                "as212",
                "abc123.",
                clubId);
        Machine machine = MachineMapper.toModel(machineDtoIn, aeSecretKey);
        machine.setMachineId(machineId);
        machine.setClubName("1232as");
        Mockito.when(updateMachineUseCase.updateMachine(Mockito.eq(clubId), Mockito.eq(machineId), Mockito.any(Machine.class))).thenReturn(machine);

        mockMvc.perform(put("/api/v1/machines/" + machineId)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(machineDtoIn)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineId").value(machineId.toString()))
                .andExpect(jsonPath("$.code").value("machineCode"))
                .andExpect(jsonPath("$.smartFridgeId").value("1232as"))
                .andExpect(jsonPath("$.smartFridgePassword").value("asdsa"))
                .andExpect(jsonPath("$.terminalId").value("123432"))
                .andExpect(jsonPath("$.rustdeskId").value("as212"))
                .andExpect(jsonPath("$.rustdeskPass").value("abc123."))
                .andExpect(jsonPath("$.clubName").value("1232as"))
                .andExpect(jsonPath("$.micronId").value("abc123"))
                .andExpect(jsonPath("$.toaSerialNumber").value("asdd12"));

        Mockito.verify(updateMachineUseCase).updateMachine(Mockito.eq(clubId), Mockito.eq(machineId), Mockito.any(Machine.class));
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when machine input is invalid")
    void shouldReturnBadRequestForInvalidMachineInput() throws Exception {
        MachineDtoIn invalidMachineDtoIn = new MachineDtoIn(
                "",
                "",
                "",
                "",
                "invalidEmail",
                "",
                "",
                "123456789",
                UUID.randomUUID());
        mockMvc.perform(put("/api/v1/machines/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidMachineDtoIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 404 NOT FOUND when machine does not exist")
    void shouldReturnNotFoundWhenMachineDoesNotExist() throws Exception {
        UUID machineId = UUID.randomUUID();
        UUID clubId = UUID.randomUUID();
        MachineDtoIn machineDtoIn = new MachineDtoIn(
                "machineCode",
                "1232as",
                "abc123.",
                "asdsa",
                "123432",
                "asdd12",
                "as212",
                "abc123.",
                clubId);
        Mockito.when(updateMachineUseCase.updateMachine(Mockito.eq(clubId), Mockito.eq(machineId), Mockito.any(Machine.class))).thenThrow(new ResourceNotFoundException("Machine not found"));

        mockMvc.perform(put("/api/v1/machines/" + machineId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(machineDtoIn)))
                .andExpect(status().isNotFound());
    }
}
