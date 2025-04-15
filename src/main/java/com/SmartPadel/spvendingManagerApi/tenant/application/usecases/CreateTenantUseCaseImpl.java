package com.SmartPadel.spvendingManagerApi.tenant.application.usecases;

import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in.CreateTenantUseCase;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.out.TenantRepositoryPort;
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
