package com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RetrieveTenantUseCase {
    Page<Tenant> getAllTenants(Pageable pageable);
    Page<Tenant> getAllTenants(String search, Pageable pageable);
    Tenant getTenantById(UUID tenantId);
}
