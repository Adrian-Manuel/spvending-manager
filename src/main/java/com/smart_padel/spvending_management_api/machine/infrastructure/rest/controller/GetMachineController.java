package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.RetrieveMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutDetail;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutPreview;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachinePagePreviewSwagger;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/machines")
@RequiredArgsConstructor
@Tag(name = "Machine", description = "Retrieve machine information")
public class GetMachineController {
    @Value("${app.AESecret_key}")
    private String aeSecretKey;
    private final RetrieveMachineUseCase retrieveMachineUseCase;

    @Operation(
            summary = "Get all machines with pagination and optional search",
            description = "Returns a paginated list of machines. Accessible to admin and user with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paginated list of machines",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = MachinePagePreviewSwagger.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity<Page<MachineDtoOutPreview>> getAllMachines(
            @Parameter(description = "Search string to filter machines by code or other criteria", example = "Smart")
            @RequestParam(required = false) String search,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MachineDtoOutPreview> machines = retrieveMachineUseCase.getAllMachines(search, pageable).map(MachineMapper::toDtoPreview);
        return new ResponseEntity<>(machines, HttpStatus.OK);
    }

    @Operation(
            summary = "Get machine by ID",
            description = "Returns detailed information of a machine by its UUID. Accessible to admin and user with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine found",
                    content = @Content(schema = @Schema(implementation = MachineDtoOutDetail.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "404", description = "Machine not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{machineId}")
    public ResponseEntity<MachineDtoOutDetail> getMachineById(
            @Parameter(description = "UUID of the machine", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID machineId) throws GeneralSecurityException {
        Machine machineRequest=retrieveMachineUseCase.getMachineById(machineId);
        return new ResponseEntity<>(MachineMapper.toDtoDetail(machineRequest,aeSecretKey), HttpStatus.OK);
    }
}
