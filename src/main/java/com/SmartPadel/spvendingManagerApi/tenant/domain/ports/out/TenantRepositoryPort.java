package com.SmartPadel.spvendingManagerApi.tenant.domain.ports.out;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
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
