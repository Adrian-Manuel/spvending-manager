package com.smart_padel.spvending_management_api.user_manager.domain.model;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
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
