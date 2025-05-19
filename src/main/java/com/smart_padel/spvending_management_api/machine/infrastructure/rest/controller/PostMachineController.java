package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.CreateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutPreview;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/v1/machines")
@RequiredArgsConstructor
@Tag(name = "Machine", description = "Create machine")
public class PostMachineController {
    @Value("${app.AESecret_key}")
    private String aeSecretKey;
    private final CreateMachineUseCase createMachineUseCase;

    @Operation(
            summary = "Create a new machine",
            description = "Creates a new machine and returns its preview information. Requires 'admin:create' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Machine created successfully",
                    content = @Content(schema = @Schema(implementation = MachineDtoOutPreview.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<MachineDtoOutPreview> createClub (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Machine information for creation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MachineDtoIn.class))
            )
            @RequestBody @Valid MachineDtoIn machineDtoIn) throws GeneralSecurityException {
        Machine machineRequest= MachineMapper.toModel(machineDtoIn, aeSecretKey);
        machineRequest=createMachineUseCase.createMachine(machineDtoIn.getClubId(), machineRequest);
        return new ResponseEntity<>(MachineMapper.toDtoPreview(machineRequest), HttpStatus.CREATED);
    }
}
