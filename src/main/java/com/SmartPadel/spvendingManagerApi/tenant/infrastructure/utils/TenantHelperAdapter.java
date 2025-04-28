package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.utils;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceAlreadyExistsException;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import java.util.UUID;
public class TenantHelperAdapter {
    public static TenantEntity getTenantOrThrow(JpaTenantRepository repo, UUID tenantId) {
        return repo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("The requested tenant was not found"));
    }
    public static void validateTenantExists(JpaTenantRepository repo, UUID tenantId) {
        if (!repo.existsById(tenantId)) {
            throw new ResourceNotFoundException("The tenant does not exist");
        }
    }
    public static void validateTenantNameNotExists(JpaTenantRepository repo, String name) {
        if (repo.existsByName(name)) {
            throw new ResourceAlreadyExistsException("There is already a tenant with that name");
        }
    }
}
