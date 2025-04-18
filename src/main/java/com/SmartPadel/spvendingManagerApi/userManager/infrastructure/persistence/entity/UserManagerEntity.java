package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity;

import java.util.UUID;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.shared.Utils.Filtrable;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
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
    @ManyToOne
    @JoinColumn(name = "tenantId", referencedColumnName = "tenantId", nullable = true)
    private TenantEntity tenantEntity;
    @Filtrable(name = "club.name")
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "clubId", referencedColumnName = "clubId", nullable = true)
    private ClubEntity club;

    public static UserManagerEntity fromDomainModel(UserManager userManager){
        return UserManagerEntity.builder()
                .userId(userManager.getUserId())
                .userName(userManager.getUserName())
                .password(userManager.getPassword())
                .micronId(userManager.getMicronId())
                .micronUser(userManager.getMicronUser())
                .micronPass(userManager.getMicronPass())
                .userType(userManager.getUserType())
                .tenantEntity(userManager.getTenantEntity())
                .club(userManager.getClub())
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
                .tenantEntity(tenantEntity)
                .club(club)
                .build();
    }

}

