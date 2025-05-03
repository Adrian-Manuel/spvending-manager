package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.CreateTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CreateTenantUseCaseImpl implements CreateTenantUseCase {
    private final TenantRepositoryPort tenantRepositoryPort;
    @Override
    public Tenant createTenant(Tenant tenant) {
        return tenantRepositoryPort.save(tenant);
    }
}
