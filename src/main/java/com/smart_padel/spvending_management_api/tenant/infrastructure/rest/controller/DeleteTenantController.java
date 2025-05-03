package com.smart_padel.spvending_management_api.tenant.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.DeleteTenantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeleteTenantController {
    private final DeleteTenantUseCase deleteTenantUseCase;
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenantById(@PathVariable UUID tenantId) {
        deleteTenantUseCase.deleteTenant(tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
