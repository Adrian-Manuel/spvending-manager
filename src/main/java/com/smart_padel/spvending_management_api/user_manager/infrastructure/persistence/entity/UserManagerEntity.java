package com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.shared.utils.Filtrable;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_managers")
@Builder
public class UserManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    @Filtrable
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "micron_id", nullable = false, unique = true)
    private String micronId;
    @Filtrable
    @Column(name = "micronUser", nullable = false, unique = true)
    private String micronUser;
    @Column(name = "micronPass", nullable = false)
    private String micronPass;
    @Column(name = "usertype", nullable = false)
    private String userType;

    @Filtrable(name = "tenantEntity.name")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenantId", referencedColumnName = "tenantId", nullable = true)
    private TenantEntity tenantEntity;

    @Filtrable(name = "clubEntity.name")
    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId", referencedColumnName = "clubId", nullable = true)
    private ClubEntity clubEntity;

    public static UserManagerEntity fromDomainModel(UserManager userManager){
        return UserManagerEntity.builder()
                .userId(userManager.getUserId())
                .userName(userManager.getUserName())
                .password(userManager.getPassword())
                .micronId(userManager.getMicronId())
                .micronUser(userManager.getMicronUser())
                .micronPass(userManager.getMicronPass())
                .userType(userManager.getUserType())
                .build();
    }
    public UserManager toDomainModel(){
        return UserManager.builder()
                .userId(userId)
                .userName(userName)
                .password(password)
                .micronId(micronId)
                .micronUser(micronUser)
                .micronPass(micronPass)
                .userType(userType)
                .tenantName(tenantEntity!=null ? tenantEntity.getName():null)
                .clubName(clubEntity!=null ? clubEntity.getName():null)
                .clubId(clubEntity!=null ? clubEntity.getClubId():null)
                .tenantId(tenantEntity!= null ? tenantEntity.getTenantId():null)
                .build();
    }
}

