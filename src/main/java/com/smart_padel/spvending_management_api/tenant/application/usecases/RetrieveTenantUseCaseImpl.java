package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.RetrieveTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class RetrieveTenantUseCaseImpl implements RetrieveTenantUseCase {
    private final TenantRepositoryPort tenantRepositoryPort;
    @Override
    public Page<Tenant> getAllTenants(Pageable pageable) {return tenantRepositoryPort.findAll(pageable);}
    @Override
    public Page<Tenant> getAllTenants(String search, Pageable pageable) {return tenantRepositoryPort.findAll(search, pageable);}
    @Override
    public Tenant getTenantById(UUID tenantId) {return tenantRepositoryPort.findById(tenantId);}
    @Override
    public List<Tenant> getAllTenantsSummary() {
        return tenantRepositoryPort.findAllTenantsSummary();
    }
}
