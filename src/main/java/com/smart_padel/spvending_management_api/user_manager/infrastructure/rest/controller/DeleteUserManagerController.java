package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.DeleteUserManagerUseCase;
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
@RequestMapping("/api/v1/user-managers")
@RequiredArgsConstructor
@RestController
@Tag(name = "User Manager", description = "Delete user manager information")
public class DeleteUserManagerController {
    private final DeleteUserManagerUseCase deleteUserManagerUseCase;

    @Operation(
            summary = "Delete user manager by ID",
            description = "Deletes a user manager by its UUID. Requires 'admin:delete' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User manager deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User manager not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "invalid UUID format")
    })
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{userManagerId}")
    public ResponseEntity<Void> deleteUserManager (
            @Parameter(description = "UUID of the user manager to delete", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID userManagerId){
        deleteUserManagerUseCase.deleteUserManager(userManagerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
