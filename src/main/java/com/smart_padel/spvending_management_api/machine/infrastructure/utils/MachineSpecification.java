package com.smart_padel.spvending_management_api.machine.infrastructure.utils;
import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.entity.MachineEntity;
import com.smart_padel.spvending_management_api.shared.utils.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;
public class MachineSpecification {
    private MachineSpecification() {
        throw new IllegalStateException("Specification class");
    }
    public static Specification<MachineEntity> withFilter(String filter) {
        return SpecificationUtils.buildFilterSpec(MachineEntity.class, filter);
    }
    public static Specification<MachineEntity> belongsToClub(UUID clubId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("club").get("clubId"), clubId);
    }
}
