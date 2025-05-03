package com.smart_padel.spvending_management_api.tenant.application.usecases;
import com.smart_padel.spvending_management_api.tenant.domain.ports.in.DeleteTenantUseCase;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DeleteTenantUseCaseImpl implements DeleteTenantUseCase {
    private final TenantRepositoryPort tenantRepositoryPort;
    @Override
    public void deleteTenant(UUID tenantId) {
        tenantRepositoryPort.deleteById(tenantId);
    }
}
