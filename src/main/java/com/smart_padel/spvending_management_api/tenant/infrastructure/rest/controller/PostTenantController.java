package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.CreateTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoIn;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutPreview;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper.TenantMapper;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenant", description = "Create a new tenant")
public class PostTenantController {
    private final CreateTenantUseCase createTenantUseCase;

    @Operation(
            summary = "Create a new tenant",
            description = "Creates a new tenant in the system. Requires 'admin:create' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tenant created successfully",
                    content = @Content(schema = @Schema(implementation = TenantDtoOutPreview.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
    })
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<TenantDtoOutPreview> createTenant(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Tenant data to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TenantDtoIn.class))
            )
        @Valid @RequestBody TenantDtoIn tenantDtoIn){
        Tenant tenantRequest=TenantMapper.toModel(tenantDtoIn);
        tenantRequest=createTenantUseCase.createTenant(tenantRequest);
        return new ResponseEntity<>(TenantMapper.toDtoPreview(tenantRequest), HttpStatus.CREATED);
    }
}
