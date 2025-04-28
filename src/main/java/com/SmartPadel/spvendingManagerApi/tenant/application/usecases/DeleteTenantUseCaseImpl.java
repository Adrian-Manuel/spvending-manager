package com.SmartPadel.spvendingManagerApi.tenant.application.usecases;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.in.DeleteTenantUseCase;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.out.TenantRepositoryPort;
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
