package com.smart_padel.spvending_management_api.machine.infrastructure.persistence.repository;

import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.entity.MachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface JpaMachineRepository extends JpaRepository<MachineEntity, UUID>, JpaSpecificationExecutor<MachineEntity> {
}
