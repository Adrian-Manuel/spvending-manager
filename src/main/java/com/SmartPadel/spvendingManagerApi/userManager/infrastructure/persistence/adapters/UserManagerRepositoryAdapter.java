package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.adapters;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.repository.JpaClubRepository;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ParamRequiredException;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceAlreadyExistsException;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.out.UserManagerRepositoryPort;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.repository.JpaUserManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (tenantId==null && clubId==null){
            throw new ParamRequiredException("some club id or tenant id is required");
        }

        if (tenantId!=null && clubId!=null){
            throw new ParamRequiredException("only one of the two parameters is needed");
        }

        if (tenantId!=null && userManager.getUserType().equals("1")){
            throw new ParamRequiredException("The user is type 1, the club id is required.");
        }

        if (clubId!=null && userManager.getUserType().equals("2")){
            throw new ParamRequiredException("The user is type 2, the tenant id is required.");
        }

        boolean userNameExists=jpaUserManagerRepository.existsByUserName(userManager.getUserName());
        boolean micronIdExists=jpaUserManagerRepository.existsByMicronId(userManager.getMicronId());
        boolean micronUserExists=jpaUserManagerRepository.existsByMicronUser(userManager.getMicronUser());

        if (userNameExists){
            throw new ResourceAlreadyExistsException("a user with that name already exists");
        }

        if (micronIdExists){
            throw new ResourceAlreadyExistsException("a user with that micron Id already exists");
        }

        if (micronUserExists){
            throw new ResourceAlreadyExistsException("a user with that micron user already exists");
        }


        if (clubId!=null){

            ClubEntity clubEntity=jpaClubRepository.findById(clubId).orElseThrow(()->new ResourceNotFoundException("club not found"));
            userManager.setClub(clubEntity);
            UserManagerEntity userManagerEntity=UserManagerEntity.fromDomainModel(userManager);
            userManagerEntity=jpaUserManagerRepository.save(userManagerEntity);

            return userManagerEntity.toDomainModel();
        }



        TenantEntity tenantEntity=jpaTenantRepository.findById(tenantId).orElseThrow(()->new ResourceNotFoundException("tenant not found"));
        userManager.setTenantEntity(tenantEntity);
        UserManagerEntity userManagerEntity=UserManagerEntity.fromDomainModel(userManager);
        userManagerEntity=jpaUserManagerRepository.save(userManagerEntity);

        return userManagerEntity.toDomainModel();
    }

    @Override
    public UserManager update(UUID tenantId, UUID clubId, UUID userManagerId, UserManager userManager) {
        return null;
    }

    @Override
    public void delete(UUID userManagerId) {

    }

    @Override
    public Page<UserManager> findAll(String search, Pageable pageable) {
        return null;
    }

    @Override
    public UserManager findById(UUID userManager) {
        return null;
    }
}
