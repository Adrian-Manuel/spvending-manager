package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.adapters;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.repository.JpaClubRepository;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.utils.ClubHelperAdapter;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;
import com.SmartPadel.spvendingManagerApi.shared.Utils.PersistenceUtils;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.utils.TenantHelperAdapter;
import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out.UserManagerRepositoryPort;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.repository.JpaUserManagerRepository;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.utils.UserManagerHelperAdapter;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.utils.UserManagerSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Transactional
@Component
@RequiredArgsConstructor
public class UserManagerRepositoryAdapter implements UserManagerRepositoryPort {

    private final JpaUserManagerRepository jpaUserManagerRepository;
    private final JpaClubRepository jpaClubRepository;
    private final JpaTenantRepository jpaTenantRepository;

    @Override
    public UserManager save(UUID tenantId, UUID clubId, UserManager userManager) {
        UserManagerHelperAdapter.validateClubOrTenant(tenantId, clubId, userManager.getUserType());
        UserManagerHelperAdapter.validateUserUniqueness(jpaUserManagerRepository, userManager);

        if (clubId != null) {
            ClubEntity clubEntity = ClubHelperAdapter.getClubOrThrow(jpaClubRepository, clubId);
            userManager.setClub(clubEntity);
        } else {
            TenantEntity tenantEntity = TenantHelperAdapter.getTenantOrThrow(jpaTenantRepository, tenantId);
            userManager.setTenantEntity(tenantEntity);
        }

        UserManagerEntity userManagerEntity = UserManagerEntity.fromDomainModel(userManager);
        userManagerEntity = jpaUserManagerRepository.save(userManagerEntity);
        return userManagerEntity.toDomainModel();
    }

    @Override
    public UserManager update(UUID tenantId, UUID clubId, UUID userManagerId, UserManager userManager) {
        UserManagerHelperAdapter.validateUserManagerExists(jpaUserManagerRepository, userManagerId);
        UserManagerHelperAdapter.validateClubOrTenant(tenantId, clubId, userManager.getUserType());

        if (clubId != null) {
            ClubEntity clubEntity = ClubHelperAdapter.getClubOrThrow(jpaClubRepository, clubId);
            userManager.setClub(clubEntity);
        } else {
            TenantEntity tenantEntity = TenantHelperAdapter.getTenantOrThrow(jpaTenantRepository, tenantId);
            userManager.setTenantEntity(tenantEntity);
        }

        userManager.setUserId(userManagerId);
        UserManagerEntity userManagerEntity = UserManagerEntity.fromDomainModel(userManager);
        userManagerEntity = jpaUserManagerRepository.save(userManagerEntity);
        return userManagerEntity.toDomainModel();
    }

    @Override
    public void delete(UUID userManagerId) {
        UserManagerHelperAdapter.validateUserManagerExists(jpaUserManagerRepository, userManagerId);
        jpaUserManagerRepository.deleteById(userManagerId);
    }



    @Override
    public Page<UserManager> findAll(String search, Pageable pageable) {
        Specification<UserManagerEntity> spec = UserManagerSpecification.withFilter(search);
        return PersistenceUtils.mapPageOrThrow(jpaUserManagerRepository.findAll(spec, pageable), "User managers not found", UserManagerEntity::toDomainModel);
    }

    @Override
    public UserManager findById(UUID userManagerId) {
        return jpaUserManagerRepository.findById(userManagerId)
                .map(UserManagerEntity::toDomainModel)
                .orElseThrow(()->new ResourceNotFoundException("there is no user manager with that id"));
    }
}
