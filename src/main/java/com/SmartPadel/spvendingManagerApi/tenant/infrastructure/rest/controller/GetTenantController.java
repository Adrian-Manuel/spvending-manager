package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.RetrieveClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.mapper.ClubMapper;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in.RetrieveTenantUseCase;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.TenantDtoOutSummary;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto.mapper.TenantMapper;
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
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class GetTenantController {
    private final RetrieveClubUseCase retrieveClubUseCase;
    private final RetrieveTenantUseCase retrieveTenantUseCase;
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity <Page<TenantDtoOutPreview>> getAllTenants(@RequestParam(required = false) String search,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TenantDtoOutPreview> tenants = (search != null)
                ? retrieveTenantUseCase.getAllTenants(search, pageable).map(TenantMapper::toDtoPreview)
                : retrieveTenantUseCase.getAllTenants(pageable).map(TenantMapper::toDtoPreview);
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDtoOutDetail> getTenantById(@PathVariable UUID tenantId){
        Tenant tenantRequest=retrieveTenantUseCase.getTenantById(tenantId);
        return new ResponseEntity<>(TenantMapper.toDtoDetail(tenantRequest), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{tenantId}/clubs")
    public ResponseEntity<Page<ClubDtoOutPreview>> getAllClubsByTenantId(@RequestParam(required = false) String search,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10")int size,
                                                                         @PathVariable UUID tenantId){
        Pageable pageable=PageRequest.of(page, size);
        Page<ClubDtoOutPreview> clubs = retrieveClubUseCase.getAllClubsByTenantId(search, tenantId,pageable).map(ClubMapper::toDtoPreview);
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/all-summary")
    public ResponseEntity<List<TenantDtoOutSummary>> getTenantsSummary(){
        List<TenantDtoOutSummary> tenantsSummary=retrieveTenantUseCase.getAllTenantsSummary().stream().map(TenantMapper::toDtoSummary).toList();
        return new ResponseEntity<>(tenantsSummary, HttpStatus.OK);
    }




}
