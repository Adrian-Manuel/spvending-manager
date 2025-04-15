package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in.UpdateTenantUseCase;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoIn;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class PutTenantController {
    private final UpdateTenantUseCase updateTenantUseCase;

    @PutMapping("/{tenantId}")
    public ResponseEntity<TenantDtoOutPreview> updateTenant(@PathVariable UUID tenantId, @Valid @RequestBody TenantDtoIn tenantDtoIn){
        Tenant tenantRequest=TenantMapper.toModel(tenantDtoIn);
        tenantRequest=updateTenantUseCase.updateTenant(tenantId, tenantRequest);
        return new ResponseEntity<>(TenantMapper.toDtoPreview(tenantRequest), HttpStatus.OK);
    }
}
