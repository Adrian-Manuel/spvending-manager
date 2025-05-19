package com.smart_padel.spvending_management_api.club.infrastructure.dto;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity.UserManagerEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
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
    @Schema(description = "UUID of the club", example = "12313432-54ab-4cde-1234-567812345678")
    private UUID clubId;

    @Schema(description = "Name of the club", example = "PadelPrixOurense")
    private String name;

    @Schema(description = "Full postal address of the club", example = "Calle Vigo, 9. 12006, Castellón de la Plana, España")
    private String address;

    @Schema(description = "Phone number of the club", example = "+34 966 123 456")
    private String phone;

    @Schema(description = "Name of the tenant to whom this club belongs", example = "PadelPrix")
    private String tenantEntityName;

    @Schema(
            description = "Names of the user managers associated with this club, if any",
            example = "[\"manager1\", \"manager2\"]"
    )
    private List<String> userManagers;

    @Schema(description = "Number of machines owned by this club", example = "5")
    private int machinesCount;
}
