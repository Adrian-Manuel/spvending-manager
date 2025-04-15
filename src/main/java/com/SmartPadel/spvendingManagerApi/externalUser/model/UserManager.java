package com.SmartPadel.spvendingManagerApi.externalUser.model;

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
@Table(name = "external_users")
public class UserManager {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
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
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenantid")
    private TenantEntity tenantEntity;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id", referencedColumnName = "clubid")
    private ClubEntity club;
}

