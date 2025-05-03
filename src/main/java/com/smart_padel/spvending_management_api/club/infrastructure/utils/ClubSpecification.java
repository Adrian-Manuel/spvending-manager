package com.smart_padel.spvending_management_api.club.infrastructure.utils;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.shared.utils.SpecificationUtils;
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
