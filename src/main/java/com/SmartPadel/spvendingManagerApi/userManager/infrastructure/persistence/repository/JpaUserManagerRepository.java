package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.repository;

import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserManagerRepository extends JpaRepository<UserManagerEntity, UUID>, JpaSpecificationExecutor<UserManagerEntity> {
    boolean existsByUserName(String username);
    boolean existsByMicronId(String micronId);
    boolean existsByMicronUser(String micronUser);
}
