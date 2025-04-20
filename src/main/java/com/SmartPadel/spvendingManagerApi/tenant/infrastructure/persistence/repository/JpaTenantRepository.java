package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.repository;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface JpaTenantRepository extends JpaRepository<TenantEntity, UUID>, JpaSpecificationExecutor<TenantEntity> {
    Boolean existsByName(String name);
}
