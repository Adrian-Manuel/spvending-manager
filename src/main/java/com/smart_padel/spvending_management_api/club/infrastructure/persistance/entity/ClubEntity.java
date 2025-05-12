package com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity.UserManagerEntity;
import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.entity.MachineEntity;
import com.smart_padel.spvending_management_api.shared.utils.Filtrable;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenantId", nullable = false, referencedColumnName = "tenantId")
    private TenantEntity tenantEntity;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MachineEntity> machineEntities;
    @OneToMany(mappedBy = "clubEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserManagerEntity> userManagerEntities;

    public static ClubEntity fromDomainModel(Club club){
        return ClubEntity.builder()
                .clubId(club.getClubId())
                .address(club.getAddress())
                .cif(club.getCif())
                .email(club.getEmail())
                .micronId(club.getMicronId())
                .name(club.getName())
                .phone(club.getPhone())
                .remark(club.getRemark())
                .accountingId(club.getAccountingId())
                .build();
    }
    public Club toDomainModel(){
        return  Club.builder()
                .clubId(clubId)
                .address(address)
                .cif(cif)
                .email(email)
                .micronId(micronId)
                .name(name)
                .phone(phone)
                .remark(remark)
                .accountingId(accountingId)
                .tenantName(tenantEntity.getName())
                .machinesCount(machineEntities !=null ? machineEntities.size() : 0)
                .managers(userManagerEntities!=null ? userManagerEntities.stream().map(UserManagerEntity::getUserName).toList(): Collections.emptyList())
                .build();
    }
}
