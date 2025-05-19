package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.UpdateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutDetail;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/machines")
@Tag(name = "Machine", description = "Update machine information")
public class PutMachineController {
    private final UpdateMachineUseCase updateMachineUseCase;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;

    @Operation(
            summary = "Update a machine",
            description = "Updates the information of a machine by its ID. Requires 'admin:update' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine updated successfully",
                    content = @Content(schema = @Schema(implementation = MachineDtoOutDetail.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{machineId}")
    public ResponseEntity<MachineDtoOutDetail> updateMachine(
            @Parameter(description = "UUID of the machine to update", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID machineId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Machine information for update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MachineDtoIn.class))
            )
            @Valid @RequestBody MachineDtoIn machineDtoIn) throws GeneralSecurityException {
        Machine machineRequest= MachineMapper.toModel(machineDtoIn, aeSecretKey);
        machineRequest=updateMachineUseCase.updateMachine(machineDtoIn.getClubId(),machineId, machineRequest);
        return new ResponseEntity<>(MachineMapper.toDtoDetail(machineRequest, aeSecretKey), HttpStatus.OK);
    }
}
