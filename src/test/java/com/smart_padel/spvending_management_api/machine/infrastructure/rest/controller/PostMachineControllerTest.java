package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.CreateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
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
@WebMvcTest(PostMachineController.class)
class PostMachineControllerTest {
    @MockitoBean
    private CreateMachineUseCase createMachineUseCase;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;
    @Test
    @DisplayName("should create machine successfully and return 201 CREATED")
    void shouldCreateMachineSuccessfully() throws Exception {
        UUID machineId= UUID.randomUUID();
        UUID clubId= UUID.randomUUID();
        MachineDtoIn machineDtoIn = new MachineDtoIn(
                "machineCode",
                "1232as",
                "abc123.",
                "asdsa",
                "123432",
                "asdd12",
                "asdd12",
                "abc123.",
                clubId);
        Machine machine= MachineMapper.toModel(machineDtoIn, aeSecretKey);
        machine.setMachineId(machineId);

        Mockito.when(createMachineUseCase.createMachine(Mockito.eq(clubId), Mockito.any(Machine.class))).thenReturn(machine);
        mockMvc.perform(post("/api/v1/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(machineDtoIn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.machineId").value(machineId.toString()))
                .andExpect(jsonPath("$.smartFridgeId").value("1232as"))
                .andExpect(jsonPath("$.terminalId").value("123432"))
                .andExpect(jsonPath("$.code").value("machineCode"));
        Mockito.verify(createMachineUseCase).createMachine(Mockito.eq(clubId), Mockito.any(Machine.class));

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
        mockMvc.perform(post("/api/v1/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidMachineDtoIn)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 400 BAD REQUEST when clubId is invalid")
    void shouldReturnBadRequestForInvalidClubId() throws Exception {
        MachineDtoIn machineDtoIn = new MachineDtoIn(
                "machineCode",
                "1232as",
                "abc123.",
                "asdsa",
                "123432",
                "asdd12",
                "asdd12",
                "abc123.",
                null);
        mockMvc.perform(post("/api/v1/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(machineDtoIn)))
                .andExpect(status().isBadRequest());
    }
}
