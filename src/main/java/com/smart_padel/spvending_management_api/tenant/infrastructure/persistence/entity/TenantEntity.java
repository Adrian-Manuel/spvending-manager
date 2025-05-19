package com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.shared.utils.Filtrable;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.tenant.domain.model.Tenant;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity.UserManagerEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Tenants")
@Schema(description = "Detailed information about a tenant")
public class TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tenantId;

    @Filtrable
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Filtrable
    @Column(name = "cif")
    private String cif;

    @Filtrable
    @Column(name = "address")
    private String address;

    @Filtrable
    @Column(name = "phone")
    private String phone;

    @Filtrable
    @Column(name = "email")
    private String email;

    @Column(name = "remark")
    private String remark;

    @Column(name = "micron_id")
    private String micronId;

    @JsonManagedReference
    @OneToMany(mappedBy = "tenantEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClubEntity> clubsEntities;

    @JsonManagedReference
    @OneToMany(mappedBy = "tenantEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserManagerEntity> userManagerEntities;

    public static TenantEntity fromDomainModel(Tenant tenant){
        return TenantEntity.builder()
                .tenantId(tenant.getTenantId())
                .address(tenant.getAddress())
                .cif(tenant.getCif())
                .email(tenant.getEmail())
                .micronId(tenant.getMicronId())
                .name(tenant.getName())
                .phone(tenant.getPhone())
                .remark(tenant.getRemark())
                .build();
    }
    public Tenant toDomainModel(){
        return  Tenant.builder()
                .tenantId(tenantId)
                .address(address)
                .cif(cif)
                .email(email)
                .micronId(micronId)
                .name(name)
                .phone(phone)
                .remark(remark)
                .clubsCount(clubsEntities !=null ? clubsEntities.size(): 0)
                .managers(userManagerEntities!=null ? userManagerEntities.stream().map(UserManagerEntity::getUserName).toList():Collections.emptyList())
                .build();
    }
}
