package com.smart_padel.spvending_management_api.user_manager.infrastructure.dto;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
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
    @Schema(description = "unique id for user manager", example = "a3928asd9889a876")
    private UUID userManagerId;
    @Schema(description = "Unique username used for login", example = "padel_admin")
    private String username;
    @Schema(description = "Unique identifier used in the Micron system", example = "MIC123456")
    private String micronId;
    @Schema(description = "Username for the Micron system", example = "micron_user_01")
    private String micronUser;
    @Schema(description = "Club associated with this user, if any", implementation = TenantEntity.class)
    private String tenantEntityName;
    @Schema(description = "Club associated with this user, if any", implementation = ClubEntity.class)
    private String clubEntityName;
}
