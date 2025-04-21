package com.SmartPadel.spvendingManagerApi.machine.infrastructure.utils;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.utils.ClubSpecification;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.entity.MachineEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class MachineSearchHelper {
    public static Specification<MachineEntity> buildMachineSearchSpec(UUID clubId, String search) {
        Specification<MachineEntity> spec = MachineSpecification.belongsToClub(clubId);
        if (search != null && !search.isBlank()) {
            spec = spec.and(MachineSpecification.withFilter(search));
        }
        return spec;
    }
}
