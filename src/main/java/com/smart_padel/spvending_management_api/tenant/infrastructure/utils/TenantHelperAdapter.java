package com.smart_padel.spvending_management_api.tenant.infrastructure.utils;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
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
