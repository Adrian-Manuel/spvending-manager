package com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.repository;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.entity.MachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface JpaMachineRepository extends JpaRepository<MachineEntity, UUID>, JpaSpecificationExecutor<MachineEntity> {
}
