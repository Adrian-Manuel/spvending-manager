package com.smart_padel.spvending_management_api.user_manager.infrastructure.utils;
import com.smart_padel.spvending_management_api.shared.utils.SpecificationUtils;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity.UserManagerEntity;
import org.springframework.data.jpa.domain.Specification;
public class UserManagerSpecification {
    UserManagerSpecification() {
        throw new IllegalStateException("Util class");
    }
    public static Specification<UserManagerEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(UserManagerEntity.class, filter);
    }
}
