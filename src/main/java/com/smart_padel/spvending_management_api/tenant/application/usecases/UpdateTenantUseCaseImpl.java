package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.UpdateTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
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
