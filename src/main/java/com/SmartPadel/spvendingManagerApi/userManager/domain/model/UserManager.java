package com.SmartPadel.spvendingManagerApi.userManager.domain.model;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserManager {
    private UUID userId;
    private String userName;
    private String password;
    private String micronId;
    private String micronUser;
    private String micronPass;
    private String userType;
    private TenantEntity tenantEntity;
    private ClubEntity club;
}
