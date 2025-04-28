package com.SmartPadel.spvendingManagerApi.tenant.application.usecases;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in.UpdateTenantUseCase;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.out.TenantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UpdateTenantUseCaseImpl implements UpdateTenantUseCase {
    private final TenantRepositoryPort tenantRepositoryPort;
    @Override
    public Tenant updateTenant(UUID tenantId, Tenant updateTenant) {return tenantRepositoryPort.update(tenantId,updateTenant);}
}
