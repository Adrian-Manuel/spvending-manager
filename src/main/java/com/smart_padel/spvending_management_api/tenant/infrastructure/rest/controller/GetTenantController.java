package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.ports.in.RetrieveClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutPreview;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubPagePreviewSwagger;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.RetrieveTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutDetail;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutPreview;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutSummary;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantPagePreviewSwagger;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper.TenantMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@Tag(name= "Tenant", description = "Retrieve tenant information")
public class GetTenantController {
    private final RetrieveClubUseCase retrieveClubUseCase;
    private final RetrieveTenantUseCase retrieveTenantUseCase;

    @Operation(
            summary = "Get all tenants",
            description = "Returns a paginated list of all tenants. Requires 'admin:read' or 'user:read' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenants retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TenantPagePreviewSwagger.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity <Page<TenantDtoOutPreview>> getAllTenants(
            @Parameter(description = "Text search filter for tenant name or attributes", example = "Acme") @RequestParam(required = false) String search,
            @Parameter(description = "Requested page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size) {
        if (size<1 || page<0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<TenantDtoOutPreview> tenants = (search != null)
                ? retrieveTenantUseCase.getAllTenants(search, pageable).map(TenantMapper::toDtoPreview)
                : retrieveTenantUseCase.getAllTenants(pageable).map(TenantMapper::toDtoPreview);
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }

    @Operation(
            summary = "Get tenant details by ID",
            description = "Returns detailed information about a tenant by its unique identifier. Requires 'admin:read' or 'user:read' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TenantDtoOutDetail.class))),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid Tenant ID",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDtoOutDetail> getTenantById(
            @Parameter(description = "Tenant UUID", required = true) @PathVariable UUID tenantId){
        Tenant tenantRequest=retrieveTenantUseCase.getTenantById(tenantId);
        return new ResponseEntity<>(TenantMapper.toDtoDetail(tenantRequest), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all clubs for a tenant",
            description = "Returns a paginated list of clubs associated with the specified tenant. Requires 'admin:read' or 'user:read' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clubs of a tenant retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ClubPagePreviewSwagger.class))),
            @ApiResponse(responseCode = "404", description = "Tenant not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid Tenant ID or pagination parameters",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{tenantId}/clubs")
    public ResponseEntity<Page<ClubDtoOutPreview>> getAllClubsByTenantId(
            @Parameter(description = "Text search filter for club name or attributes", example = "Padel Club") @RequestParam(required = false) String search,
            @Parameter(description = "Requested page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10")int size,
            @Parameter(description = "Tenant UUID", required = true) @PathVariable UUID tenantId){
        Pageable pageable=PageRequest.of(page, size);
        Page<ClubDtoOutPreview> clubs = retrieveClubUseCase.getAllClubsByTenantId(search, tenantId,pageable).map(ClubMapper::toDtoPreview);
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    @Operation(
            summary = "Get summary of all tenants",
            description = "Returns a summary list of all tenants in the system. Requires 'admin:read' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant summary list retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TenantDtoOutSummary.class)))),
            @ApiResponse(responseCode = "404", description = "Tenants not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
    })
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/all-summary")
    public ResponseEntity<List<TenantDtoOutSummary>> getTenantsSummary(){
        List<TenantDtoOutSummary> tenantsSummary=retrieveTenantUseCase.getAllTenantsSummary().stream().map(TenantMapper::toDtoSummary).toList();
        return new ResponseEntity<>(tenantsSummary, HttpStatus.OK);
    }
}
