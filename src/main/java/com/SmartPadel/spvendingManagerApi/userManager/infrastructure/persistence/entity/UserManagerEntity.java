package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity;

import java.util.UUID;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_managers")
public class UserManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    
    @Column(name = "username", nullable = false, unique = true)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "micron_id", nullable = false, unique = true)
    private String micronId;
    @Column(name = "micron_user", nullable = false, unique = true)
    private String micronUser;
    @Column(name = "micronPass", nullable = false)
    private String micronPass;
    @Column(name = "user_type", nullable = false, columnDefinition = "int")
    private String userType;

    @ManyToOne
    @JoinColumn(name = "tenantId", referencedColumnName = "tenantId")
    private TenantEntity tenantEntity;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "clubId", referencedColumnName = "clubId")
    private ClubEntity club;
}

