package com.smart_padel.spvending_management_api.club.infrastructure.persistance.adapters;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.out.ClubRepositoryPort;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.repository.JpaClubRepository;
import com.smart_padel.spvending_management_api.club.infrastructure.utils.ClubHelperAdapter;
import com.smart_padel.spvending_management_api.club.infrastructure.utils.ClubSearchHelper;
import com.smart_padel.spvending_management_api.club.infrastructure.utils.ClubSpecification;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import com.smart_padel.spvending_management_api.shared.utils.PersistenceUtils;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.smart_padel.spvending_management_api.tenant.infrastructure.utils.TenantHelperAdapter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
@Transactional
@Component
@RequiredArgsConstructor
public class ClubRepositoryAdapter implements ClubRepositoryPort {
    private final JpaClubRepository jpaClubRepository;
    private final JpaTenantRepository jpaTenantRepository;
    @Override
    public Page<Club> findAll(String search, Pageable pageable) {
        Specification<ClubEntity> spec = ClubSpecification.withFilter(search);
        return PersistenceUtils.mapPageOrThrow(jpaClubRepository.findAll(spec, pageable), "Clubs not found", ClubEntity::toDomainModel);
    }
    @Override
    public Page<Club> findAllClubsByTenantId(String search, UUID tenantId, Pageable pageable) {
        TenantHelperAdapter.validateTenantExists(jpaTenantRepository, tenantId);
        Specification<ClubEntity> spec= ClubSearchHelper.buildClubSearchSpec(tenantId,search);
        return PersistenceUtils.mapPageOrThrow(jpaClubRepository.findAll(spec, pageable), "No clubs found for this tenant", ClubEntity::toDomainModel);
    }
    @Override
    public List<Club> findAllClubsSummary() {
        return PersistenceUtils.mapListOrThrow(jpaClubRepository.findAll(), "Clubs not found", ClubEntity::toDomainModel);
    }
    @Override
    public Club save(UUID tenantId, Club club) {
        TenantEntity tenantEntity = TenantHelperAdapter.getTenantOrThrow(jpaTenantRepository, tenantId);
        ClubEntity clubEntity=ClubEntity.fromDomainModel(club);
        clubEntity.setTenantEntity(tenantEntity);
        return jpaClubRepository.save(clubEntity).toDomainModel();
    }
    @Override
    public Club findById(UUID clubId) {
        return jpaClubRepository.findById(clubId)
                .map(ClubEntity::toDomainModel)
                .orElseThrow(() -> new ResourceNotFoundException("There is no club with that Id"));
    }
    @Override
    public Club update(UUID tenantId,UUID clubId, Club club) {
        ClubHelperAdapter.validateClubExists(jpaClubRepository,clubId);
        TenantEntity tenantEntity=TenantHelperAdapter.getTenantOrThrow(jpaTenantRepository, tenantId);
        club.setClubId(clubId);
        ClubEntity clubEntity=ClubEntity.fromDomainModel(club);
        clubEntity.setTenantEntity(tenantEntity);
        return jpaClubRepository.save(clubEntity).toDomainModel();
    }
    @Override
    public void deleteById(UUID clubId) {
        ClubHelperAdapter.validateClubExists(jpaClubRepository, clubId);
        jpaClubRepository.deleteById(clubId);
    }
}
