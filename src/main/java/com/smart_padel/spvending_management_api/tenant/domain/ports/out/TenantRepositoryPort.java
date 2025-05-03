package com.smart_padel.spvending_management_api.tenant.domain.ports.out;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
public interface TenantRepositoryPort {
    Page<Tenant> findAll(Pageable pageable);
    Page<Tenant> findAll(String search, Pageable pageable);
    List<Tenant> findAllTenantsSummary();
    Tenant save(Tenant tenant);
    Tenant findById(UUID tenantId);
    Tenant update(UUID tenantId, Tenant tenant);
    void deleteById(UUID tenantId);
}
