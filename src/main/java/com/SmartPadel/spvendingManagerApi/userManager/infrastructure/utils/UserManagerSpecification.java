package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.utils;

import com.SmartPadel.spvendingManagerApi.shared.Utils.SpecificationUtils;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserManagerSpecification {
    public static Specification<UserManagerEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(UserManagerEntity.class, filter);
    }
}
