package com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.adapters;
import com.smart_padel.spvending_management_api.shared.utils.PersistenceUtils;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.tenant.domain.ports.out.TenantRepositoryPort;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.smart_padel.spvending_management_api.tenant.infrastructure.utils.TenantHelperAdapter;
import com.smart_padel.spvending_management_api.tenant.infrastructure.utils.TenantSpecification;
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
public class TenantRepositoryAdapter implements TenantRepositoryPort {
    private final JpaTenantRepository jpaTenantRepository;
    @Override
    public Tenant save(Tenant tenant) {
        TenantHelperAdapter.validateTenantNameNotExists(jpaTenantRepository, tenant.getName());
        TenantEntity savedEntity = jpaTenantRepository.save(TenantEntity.fromDomainModel(tenant));
        return savedEntity.toDomainModel();
    }
    @Override
    public Tenant findById(UUID tenantId){
        return TenantHelperAdapter.getTenantOrThrow(jpaTenantRepository, tenantId).toDomainModel();
    }
    @Override
    public Page<Tenant> findAll(Pageable pageable) {
        return PersistenceUtils.mapPageOrThrow(jpaTenantRepository.findAll(pageable),"No tenants have been added yet",TenantEntity::toDomainModel);
    }
    @Override
    public Page<Tenant> findAll(String search, Pageable pageable) {
        Specification<TenantEntity> spec = TenantSpecification.withFilter(search);
       return PersistenceUtils.mapPageOrThrow(jpaTenantRepository.findAll(spec, pageable),"Tenants not found" , TenantEntity::toDomainModel);
    }
    @Override
    public List<Tenant> findAllTenantsSummary() {
        return PersistenceUtils.mapListOrThrow(jpaTenantRepository.findAll(), "Tenants not found", TenantEntity::toDomainModel);
    }
    @Override
    public Tenant update(UUID tenantId, Tenant tenant) {
        TenantHelperAdapter.validateTenantExists(jpaTenantRepository, tenantId);
        tenant.setTenantId(tenantId);
        TenantEntity tenantEntity=TenantEntity.fromDomainModel(tenant);
        return jpaTenantRepository.save(tenantEntity).toDomainModel();
    }
    @Override
    public void deleteById(UUID tenantId) {
        TenantHelperAdapter.validateTenantExists(jpaTenantRepository, tenantId);
        jpaTenantRepository.deleteById(tenantId);
    }
}
