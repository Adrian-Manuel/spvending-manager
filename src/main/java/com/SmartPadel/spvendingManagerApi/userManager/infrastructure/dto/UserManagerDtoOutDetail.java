package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO containing information for external user managers who can be associated with tenants or clubs")
public class UserManagerDtoOutDetail {
    @Schema(
            description = "unique id for user manager",
            example = "a3928asd9889a876"
    )
    private UUID userManagerId;

    @Schema(
            description = "Unique username used for login",
            example = "padel_admin"
    )
    private String username;

    @Schema(
            description = "Password used for authentication",
            example = "securePassword123!"
    )
    private String password;

    @Schema(
            description = "Unique identifier used in the Micron system",
            example = "MIC123456"
    )
    private String micronId;

    @Schema(
            description = "Username for the Micron system",
            example = "micron_user_01"
    )
    private String micronUser;


    @Schema(
            description = "Password for the Micron system",
            example = "micronPass123"
    )
    private String micronPass;


    @Schema(
            description = "Type of user (e.g. 1 TENANT_ADMIN, 2 CLUB_ADMIN)",
            example = "1"
    )
    private String userType;


    @Schema(
            description = "Club associated with this user, if any",
            implementation = TenantEntity.class
    )
    private String tenantEntityName;

    @Schema(
            description = "Club associated with this user, if any",
            implementation = ClubEntity.class
    )
    private String clubEntityName;
}
