package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.UpdateTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoIn;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutDetail;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper.TenantMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tenants")
@Tag(name= "Tenant", description = "Update tenant information")
public class PutTenantController {
    private final UpdateTenantUseCase updateTenantUseCase;

    @Operation(
            summary = "Update a tenant",
            description = "Updates the information of a tenant by ID. Requires 'admin:update' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tenant updated successfully",
                    content = @Content(schema = @Schema(implementation = TenantDtoOutDetail.class))
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
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tenant not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "There is already a tenant with that name",
                    content = @Content
            )
    })
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{tenantId}")
    public ResponseEntity<TenantDtoOutDetail> updateTenant(
            @Parameter(description = "UUID of the tenant to update", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID tenantId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Tenant data to update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TenantDtoIn.class))
            )
            @Valid @RequestBody TenantDtoIn tenantDtoIn){
        Tenant tenantRequest=TenantMapper.toModel(tenantDtoIn);
        tenantRequest=updateTenantUseCase.updateTenant(tenantId, tenantRequest);
        return new ResponseEntity<>(TenantMapper.toDtoDetail(tenantRequest), HttpStatus.OK);
    }
}
