package com.smart_padel.spvending_management_api.tenant.domain.ports.in;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
public interface RetrieveTenantUseCase {
    Page<Tenant> getAllTenants(Pageable pageable);
    Page<Tenant> getAllTenants(String search, Pageable pageable);
    Tenant getTenantById(UUID tenantId);
    List<Tenant> getAllTenantsSummary();
}
