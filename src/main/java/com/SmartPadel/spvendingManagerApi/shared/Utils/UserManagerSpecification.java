package com.SmartPadel.spvendingManagerApi.shared.Utils;

import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserManagerSpecification {
    public static Specification<UserManagerEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(UserManagerEntity.class, filter);
    }
}
