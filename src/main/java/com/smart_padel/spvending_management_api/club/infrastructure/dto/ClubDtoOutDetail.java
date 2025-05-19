package com.smart_padel.spvending_management_api.club.infrastructure.dto;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.persistence.entity.UserManagerEntity;
import com.smart_padel.spvending_management_api.tenant.infrastructure.persistence.entity.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO containing detailed information about a club, including contact info and associated tenant and user managers.")
public class ClubDtoOutDetail {
    @Schema(description = "UUID of the club", example = "12313432-54ab-4cde-1234-567812345678")
    private UUID clubId;

    @Schema(description = "Name of the club", example = "PadelPrixOurense")
    private String name;

    @Schema(description = "Tax Identification Code (CIF) of the club", example = "A345F")
    private String cif;

    @Schema(description = "Full postal address of the club", example = "Calle Vigo, 9. 12006, Castellón de la Plana, España")
    private String address;

    @Schema(description = "Phone number of the club", example = "+34 966 123 456")
    private String phone;

    @Schema(description = "Contact email of the club", example = "info@clubs.com")
    private String email;

    @Schema(description = "Optional remarks or notes about the club", example = "Club prefers weekend tournaments only.")
    private String remark;

    @Schema(description = "ID used for accounting purposes", example = "ACC-4567")
    private String accountingId;

    @Schema(description = "Micron ID associated with this club", example = "82160004")
    private String micronId;

    @Schema(description = "Name of the tenant to whom this club belongs", example = "PadelPrix")
    private String tenantEntityName;

    @Schema(
            description = "Names of the user managers associated with this club, if any",
            example = "[\"manager1\", \"manager2\"]"
    )
    private List<String> userManagers;
}
