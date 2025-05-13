package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.UpdateTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoIn;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutDetail;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper.TenantMapper;
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
