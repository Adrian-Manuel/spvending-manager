package com.smart_padel.spvending_management_api.club.infrastructure.persistance.repository;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public interface JpaClubRepository extends JpaRepository<ClubEntity, UUID>, JpaSpecificationExecutor<ClubEntity> {}
