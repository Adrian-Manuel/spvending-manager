package com.SmartPadel.spvendingManagerApi.machine.infrastructure.utils;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.entity.MachineEntity;
import com.SmartPadel.spvendingManagerApi.shared.Utils.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;
public class MachineSpecification {
    public static Specification<MachineEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(MachineEntity.class, filter);
    }
    public static Specification<MachineEntity> belongsToClub(UUID clubId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("club").get("clubId"), clubId);
    }
}
