package com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.repository;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.UUID;

public interface JpaClubRepository extends JpaRepository<ClubEntity, UUID>, JpaSpecificationExecutor<ClubEntity> {
    Boolean existsByName(String name);
    Optional<ClubEntity> findByName(String name);
}
