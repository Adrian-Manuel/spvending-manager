package com.SmartPadel.spvendingManagerApi.club.infrastructure.utils;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
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
