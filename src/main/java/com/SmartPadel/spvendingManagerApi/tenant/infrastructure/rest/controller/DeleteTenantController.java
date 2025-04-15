package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.rest.controller;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in.DeleteTenantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class DeleteTenantController {

    private final DeleteTenantUseCase deleteTenantUseCase;

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenantById(@PathVariable UUID tenantId) {
        deleteTenantUseCase.deleteTenant(tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
