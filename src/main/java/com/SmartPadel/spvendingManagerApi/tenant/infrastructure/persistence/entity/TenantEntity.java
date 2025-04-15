package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity;
import java.util.List;
import java.util.UUID;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.externalUser.model.UserManager;
import com.SmartPadel.spvendingManagerApi.tenant.domain.model.Tenant;
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

    @Filtrable
    @Column(name = "remark")
    private String remark;

    @Column(name = "micron_id")
    private String micronId;

    @OneToMany(mappedBy = "tenantEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClubEntity> clubs;


    @OneToMany(mappedBy = "tenantEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserManager> userManagers;

    public static TenantEntity fromDomainModel(Tenant tenant){
        return TenantEntity.builder()
                .tenantId(tenant.getTenantId())
                .address(tenant.getAddress())
                .cif(tenant.getCif())
                .clubs(tenant.getClubs())
                .email(tenant.getEmail())
                .micronId(tenant.getMicronId())
                .name(tenant.getName())
                .phone(tenant.getPhone())
                .remark(tenant.getRemark())
                .userManagers(tenant.getUserManagers())
                .build();
    }

    public Tenant toDomainModel(){
        return  Tenant.builder()
                .tenantId(tenantId)
                .address(address)
                .cif(cif)
                .clubs(clubs)
                .email(email)
                .micronId(micronId)
                .name(name)
                .phone(phone)
                .remark(remark)
                .userManagers(userManagers)
                .build();
    }
}
