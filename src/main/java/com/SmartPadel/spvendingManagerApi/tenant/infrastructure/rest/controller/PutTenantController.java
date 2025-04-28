package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.rest.controller;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in.UpdateTenantUseCase;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoIn;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.mapper.TenantMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/tenants")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PutTenantController {
    private final UpdateTenantUseCase updateTenantUseCase;
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{tenantId}")
    public ResponseEntity<TenantDtoOutDetail> updateTenant(@PathVariable UUID tenantId, @Valid @RequestBody TenantDtoIn tenantDtoIn){
        Tenant tenantRequest=TenantMapper.toModel(tenantDtoIn);
        tenantRequest=updateTenantUseCase.updateTenant(tenantId, tenantRequest);
        return new ResponseEntity<>(TenantMapper.toDtoDetail(tenantRequest), HttpStatus.OK);
    }
}
