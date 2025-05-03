package com.smart_padel.spvending_management_api.user_manager.infrastructure.dto;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Validated
@Schema(description = "DTO input information for external user managers who can be associated with tenants or clubs")
public class UserManagerDtoIn {
    @NotNull(message = "The username is required")
    @Schema(description = "Unique username used for login", example = "padel_admin")
    private String username;
    @NotNull(message = "The password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "the password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, and 1 number")
    @Schema(description = "Password used for authentication", example = "securePassword123!")
    private String password;
    @NotNull(message = "the micron Id is required")
    @Schema(description = "Unique identifier used in the Micron system", example = "MIC123456")
    private String micronId;
    @NotNull(message = "The micron user is required")
    @Schema(description = "Username for the Micron system", example = "micron_user_01")
    private String micronUser;
    @NotNull(message = "The micron password is required")
    @Schema(description = "Password for the Micron system", example = "micronPass123")
    private String micronPass;
    @Size(min = 1, max = 1, message = "this field just accept 1 character, 1 or 2")
    @NotNull(message = "The user type is required")
    @Schema(description = "Type of user (e.g. 1 TENANT_ADMIN, 2 CLUB_ADMIN)", example = "1")
    private String userType;
    @Schema(description = "tenant id associated with this user, if any", implementation = TenantEntity.class)
    private UUID tenantEntityId;
    @Schema(description = "Club id associated with this user, if any", implementation = ClubEntity.class)
    private UUID clubEntityId;
}
