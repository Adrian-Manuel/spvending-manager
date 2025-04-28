package com.SmartPadel.spvendingManagerApi.club.infrastructure.dto;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@Schema(description = "DTO that contains the main information of a Club entity, including related tenant and machines")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubDtoOutPreview {
    @Schema(description = "id of the club", example = "1231343254")
    private UUID clubId;
    @Schema(description = "Name of the club", example = "PadelPrixOurense")
    private String name;
    @Schema(description = "Full postal address of the club", example = "Calle Vigo, 9. 12006, Castellón de la Plana, España")
    private String address;
    @Schema(description = "Phone number of the club", example = "+34 966 123 456")
    private String phone;
    @Schema(description = "Tenant to whom this club belongs", implementation = TenantEntity.class)
    private String tenantEntityName;
    @Schema(description = "Users manager associated with this club, if any", implementation = UserManagerEntity.class)
    private List<String> userManagers;
    @Schema(description = "number of machines owned by this club", implementation = UserManagerEntity.class)
    private int machinesCount;
}
