package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.DeleteTenantUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@Tag(name= "Tenant", description = "Delete tenant information")
public class DeleteTenantController {
    private final DeleteTenantUseCase deleteTenantUseCase;

    @Operation(
            summary = "Delete tenant by ID",
            description = "Deletes a tenant by its UUID. Requires 'admin:delete' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tenant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tenant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenantById(@PathVariable UUID tenantId) {
        deleteTenantUseCase.deleteTenant(tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
