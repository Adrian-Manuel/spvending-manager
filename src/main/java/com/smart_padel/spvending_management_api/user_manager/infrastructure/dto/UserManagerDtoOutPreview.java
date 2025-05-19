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
@Schema(description = "DTO containing preview information for external user managers who can be associated with tenants or clubs")
public class UserManagerDtoOutPreview {
    @Schema(description = "Unique id for user manager", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID userManagerId;

    @Schema(description = "Unique username used for login", example = "padel_admin")
    private String username;

    @Schema(description = "Unique identifier used in the Micron system", example = "MIC123456")
    private String micronId;

    @Schema(description = "Username for the Micron system", example = "micron_user_01")
    private String micronUser;

    @Schema(description = "Tenant name associated with this user, if any", example = "PadelPrix Group")
    private String tenantEntityName;

    @Schema(description = "Club name associated with this user, if any", example = "PadelPrix Ourense")
    private String clubEntityName;
}
