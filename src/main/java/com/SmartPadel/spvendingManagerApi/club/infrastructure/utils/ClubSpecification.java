package com.SmartPadel.spvendingManagerApi.club.infrastructure.utils;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.shared.Utils.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;
public class ClubSpecification {
    public static Specification<ClubEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(ClubEntity.class, filter);
    }
    public static Specification<ClubEntity> belongsToTenant(UUID tenantId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tenantEntity").get("tenantId"), tenantId);
    }
}
