package com.smart_padel.spvending_management_api.tenant.infrastructure.utils;
import com.smart_padel.spvending_management_api.shared.utils.SpecificationUtils;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import org.springframework.data.jpa.domain.Specification;
public class TenantSpecification {
    private TenantSpecification() {
        throw new IllegalStateException("Util class");
    }
    public static Specification<TenantEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(TenantEntity.class, filter);
    }
}