package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.CreateTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoIn;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.TenantDtoOutPreview;
import com.smart_padel.spvending_management_api.tenant.infrastructure.dto.mapper.TenantMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/tenants")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostTenantController {
    private final CreateTenantUseCase createTenantUseCase;
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<TenantDtoOutPreview> createTenant(@RequestBody @Valid TenantDtoIn tenantDtoIn){
        Tenant tenantRequest=TenantMapper.toModel(tenantDtoIn);
        tenantRequest=createTenantUseCase.createTenant(tenantRequest);
        return new ResponseEntity<>(TenantMapper.toDtoPreview(tenantRequest), HttpStatus.CREATED);
    }
}
