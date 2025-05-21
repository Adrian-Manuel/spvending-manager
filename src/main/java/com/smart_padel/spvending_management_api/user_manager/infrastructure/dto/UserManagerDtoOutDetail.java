package com.smart_padel.spvending_management_api.user_manager.infrastructure.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO containing information for external user managers who can be associated with tenants or clubs")
public class UserManagerDtoOutDetail {
    @Schema(description = "Unique id for user manager", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID userManagerId;

    @Schema(description = "Unique username used for login", example = "padel_admin")
    private String username;

    @Schema(description = "Password used for authentication", example = "securePassword123!")
    private String password;

    @Schema(description = "Unique identifier used in the Micron system", example = "MIC123456")
    private String micronId;

    @Schema(description = "Username for the Micron system", example = "micron_user_01")
    private String micronUser;

    @Schema(description = "Password for the Micron system", example = "micronPass123")
    private String micronPass;

    @Schema(description = "Type of user (e.g. 1 TENANT_ADMIN, 2 CLUB_ADMIN)", example = "1")
    private String userType;

    @Schema(description = "Tenant name associated with this user, if any", example = "PadelPrix Group")
    private String tenantEntityName;

    @Schema(description = "Tenant ID associated with this user, if any", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID tenantEntityId;

    @Schema(description = "Club name associated with this user, if any", example = "PadelPrix Ourense")
    private String clubEntityName;

    @Schema(description = "Club ID associated with this user, if any", example = "cde1f5a8-75b2-4aa9-9826-0bd7f6f1a2bd")
    private UUID clubEntityId;
}
