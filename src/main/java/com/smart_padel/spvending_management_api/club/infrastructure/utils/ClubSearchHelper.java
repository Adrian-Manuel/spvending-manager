package com.smart_padel.spvending_management_api.club.infrastructure.utils;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;
public class ClubSearchHelper {
    public static Specification<ClubEntity> buildClubSearchSpec(UUID tenantId, String search) {
        Specification<ClubEntity> spec = ClubSpecification.belongsToTenant(tenantId);
        if (search != null && !search.isBlank()) {
            spec = spec.and(ClubSpecification.withFilter(search));
        }
        return spec;
    }
}
