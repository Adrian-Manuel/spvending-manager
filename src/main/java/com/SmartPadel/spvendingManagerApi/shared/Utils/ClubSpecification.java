package com.SmartPadel.spvendingManagerApi.shared.Utils;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import org.springframework.data.jpa.domain.Specification;

public class ClubSpecification {
    public static Specification<ClubEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(ClubEntity.class, filter);
    }
}
