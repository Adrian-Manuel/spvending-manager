package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.DeleteMachineUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
@RequestMapping("/api/v1/machines")
@RestController
@RequiredArgsConstructor
@Tag(name = "Machine", description = "Delete machine information")
public class DeleteMachineController {
    private final DeleteMachineUseCase deleteMachineUseCase;

    @Operation(
            summary = "Delete machine by ID",
            description = "Deletes a machine by its UUID. Requires 'admin:delete' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Machine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Machine not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{machineId}")
    public ResponseEntity<Void> deleteMachine(
            @Parameter(description = "UUID of the machine to delete", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID machineId){
        deleteMachineUseCase.deleteMachine(machineId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
