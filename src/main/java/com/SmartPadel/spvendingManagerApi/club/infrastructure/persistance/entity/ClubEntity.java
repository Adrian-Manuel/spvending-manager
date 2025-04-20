package com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity;
import java.util.List;
import java.util.UUID;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import com.SmartPadel.spvendingManagerApi.machine.modelsV1.Machine;
import com.SmartPadel.spvendingManagerApi.shared.Utils.Filtrable;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="clubs")
public class ClubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clubId;

    @Filtrable
    @Column(name="name", nullable=false)
    private String name;
    @Column(name="cif", nullable=false)
    private String cif;
    @Filtrable
    @Column(name="address")
    private String address;
    @Filtrable
    @Column( name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "remark")
    private String remark;
    @Column(name = "accountingId", unique = true, nullable = false)
    private String accountingId;
    @Column(name = "micronId", unique = true)
    private String micronId;


    @Filtrable(name="tenantEntity.name")
    @ManyToOne
    @JoinColumn(name = "tenantId", nullable = false, referencedColumnName = "tenantId")
    private TenantEntity tenantEntity;


    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Machine> machines;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserManagerEntity> userManagerEntities;

    public static ClubEntity fromDomainModel(Club club){
        return ClubEntity.builder()
                .clubId(club.getClubId())
                .address(club.getAddress())
                .cif(club.getCif())
                .machines(club.getMachines())
                .email(club.getEmail())
                .micronId(club.getMicronId())
                .name(club.getName())
                .phone(club.getPhone())
                .remark(club.getRemark())
                .accountingId(club.getAccountingId())
                .tenantEntity(club.getTenantEntity())
                .userManagerEntities(club.getUserManagerEntities())
                .build();
    }

    public Club toDomainModel(){
        return  Club.builder()
                .clubId(clubId)
                .address(address)
                .cif(cif)
                .machines(machines)
                .email(email)
                .micronId(micronId)
                .name(name)
                .phone(phone)
                .remark(remark)
                .accountingId(accountingId)
                .tenantEntity(tenantEntity)
                .userManagerEntities(userManagerEntities)
                .build();
    }
}
