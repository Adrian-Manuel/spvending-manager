package com.SmartPadel.spvendingManagerApi.externalUser.model.dto;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@Schema(description = "DTO containing information for external user managers who can be associated with tenants or clubs")
public class UserManagerDTO {

    @NotNull(message = "The username is required")
    @Schema(
            description = "Unique username used for login",
            example = "padel_admin"
    )
    private String username;

    @NotNull(message = "The password is required")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "the password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, and 1 number"
    )
    @Schema(
            description = "Password used for authentication",
            example = "securePassword123!"
    )
    private String password;

    @NotNull(message = "The micron ID is required")
    @Schema(
            description = "Unique identifier used in the Micron system",
            example = "MIC123456"
    )
    private String micronId;

    @NotNull(message = "The micron user is required")
    @Schema(
            description = "Username for the Micron system",
            example = "micron_user_01"
    )
    private String micronUser;

    @NotNull(message = "The micron password is required")
    @Schema(
            description = "Password for the Micron system",
            example = "micronPass123"
    )
    private String micronPass;

    @NotNull(message = "The user type is required")
    @Schema(
            description = "Type of user (e.g. 1 TENANT_ADMIN, 2 CLUB_ADMIN)",
            example = "1"
    )
    private String userType;

    @NotNull(message = "The tenant is required")
    @Schema(
            description = "Tenant to which the user belongs",
            implementation = TenantEntity.class
    )
    private TenantEntity tenantEntity;

    @Schema(
            description = "Club associated with this user, if any",
            implementation = ClubEntity.class
    )
    private ClubEntity club;
}
