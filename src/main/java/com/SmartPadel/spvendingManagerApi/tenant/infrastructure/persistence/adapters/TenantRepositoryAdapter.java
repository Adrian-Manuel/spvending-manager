package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.adapters;

import com.SmartPadel.spvendingManagerApi.shared.Exceptions.NotResourcesFoundException;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceAlreadyExistsException;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
import com.SmartPadel.spvendingManagerApi.tenant.domain.ports.out.TenantRepositoryPort;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.repository.JpaTenantRepository;
import com.SmartPadel.spvendingManagerApi.shared.Utils.TenantSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Transactional
@Component
@RequiredArgsConstructor
public class TenantRepositoryAdapter implements TenantRepositoryPort {

    private final JpaTenantRepository jpaTenantRepository;

    @Override
    public Tenant save(Tenant tenant) {
        TenantEntity tenantEntity=TenantEntity.fromDomainModel(tenant);
        Boolean tenantNameExist=jpaTenantRepository.existsByName(tenantEntity.getName());

        if (tenantNameExist){
            throw new ResourceAlreadyExistsException("There is already a tenant with that name");
        }

        tenantEntity=jpaTenantRepository.save(tenantEntity);
        return tenantEntity.toDomainModel();
    }

    @Override
    public Tenant findById(UUID tenantId){
        TenantEntity tenantEntity=jpaTenantRepository.findById(tenantId).orElseThrow(()->new ResourceNotFoundException("There is no tenant with that Id"));
        return tenantEntity.toDomainModel();
    }

    @Override
    public Page<Tenant> findAll(Pageable pageable) {
        Page<TenantEntity> tenantsPage=jpaTenantRepository.findAll(pageable);
        if (tenantsPage.isEmpty()){
            throw new NotResourcesFoundException("No tenants have been added yet");
        }
        return tenantsPage.map(TenantEntity::toDomainModel);
    }

    @Override
    public Page<Tenant> findAll(String search, Pageable pageable) {
        Specification<TenantEntity> spec = TenantSpecification.withFilter(search);
        Page<TenantEntity> tenantsPage=jpaTenantRepository.findAll(spec, pageable);
        if (tenantsPage.isEmpty()){
            throw new NotResourcesFoundException("Tenants not found");
        }

        return tenantsPage.map(TenantEntity::toDomainModel);
    }

    @Override
    public Tenant update(UUID tenantId, Tenant tenant) {
        boolean tenantExist=jpaTenantRepository.existsById(tenantId);
        boolean nameTenantExist=jpaTenantRepository.existsByName(tenant.getName());
        if(!tenantExist){
            throw new ResourceNotFoundException("The tenant does not exist");
        }

        if (nameTenantExist){
            throw new ResourceAlreadyExistsException("There is already a tenant with that name");
        }

        tenant.setTenantId(tenantId);
        TenantEntity tenantEntity=TenantEntity.fromDomainModel(tenant);

        tenantEntity=jpaTenantRepository.save(tenantEntity);
        return tenantEntity.toDomainModel();
    }

    @Override
    public void deleteById(UUID tenantId) {
        boolean tenantExist=jpaTenantRepository.existsById(tenantId);

        if (!tenantExist){
            throw new ResourceNotFoundException("the tenant does not exist");
        }

        jpaTenantRepository.deleteById(tenantId);

    }
}
