package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.utils;
import com.SmartPadel.spvendingManagerApi.shared.Utils.SpecificationUtils;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import org.springframework.data.jpa.domain.Specification;
public class TenantSpecification {
    public static Specification<TenantEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(TenantEntity.class, filter);
    }
}