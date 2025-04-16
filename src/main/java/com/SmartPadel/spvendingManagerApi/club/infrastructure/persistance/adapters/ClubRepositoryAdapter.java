package com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.adapters;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.out.ClubRepositoryPort;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.shared.Utils.ClubSpecification;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.repository.JpaClubRepository;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.NotResourcesFoundException;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.repository.JpaTenantRepository;
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
public class ClubRepositoryAdapter implements ClubRepositoryPort {

    private final JpaClubRepository jpaClubRepository;
    private final JpaTenantRepository jpaTenantRepository;

    @Override
    public Page<Club> findAll(Pageable pageable) {
        Page<ClubEntity> clubsPage=jpaClubRepository.findAll(pageable);
        if (clubsPage.isEmpty()){
            throw new NotResourcesFoundException("No clubs have been added yet");
        }
        return clubsPage.map(ClubEntity::toDomainModel);
    }

    @Override
    public Page<Club> findAll(String search, Pageable pageable) {
        Specification<ClubEntity> spec = ClubSpecification.withFilter(search);
        Page<ClubEntity> clubsPage=jpaClubRepository.findAll(spec,pageable);
        if (clubsPage.isEmpty()){
            throw new NotResourcesFoundException("Clubs not found");
        }

        return clubsPage.map(ClubEntity::toDomainModel);
    }

    @Override
    public Club save(UUID tenantId, Club club) {
        TenantEntity tenantEntity= jpaTenantRepository.findById(tenantId).orElseThrow(()->new ResourceNotFoundException("The requested tenant was not found"));
        ClubEntity clubEntity=ClubEntity.fromDomainModel(club);
        clubEntity.setTenantEntity(tenantEntity);
        clubEntity=jpaClubRepository.save(clubEntity);
        return clubEntity.toDomainModel();
    }

    @Override
    public Club findById(UUID clubId) {
        ClubEntity clubEntity=jpaClubRepository.findById(clubId).orElseThrow(()->new ResourceNotFoundException("There is no club with that Id"));
        return clubEntity.toDomainModel();
    }

    @Override
    public Club update(UUID clubId, Club club) {
        return null;
    }

    @Override
    public void deleteById(UUID clubId) {

    }
}
