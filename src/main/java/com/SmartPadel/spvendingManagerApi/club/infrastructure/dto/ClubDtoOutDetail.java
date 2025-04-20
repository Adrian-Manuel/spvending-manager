package com.SmartPadel.spvendingManagerApi.club.infrastructure.dto;

import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.persistence.entity.UserManagerEntity;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ClubDtoOutDetail {
    @Schema(
            description = "id of the club",
            example = "1231343254"
    )
    private UUID clubId;

    @Schema(
            description = "Name of the club",
            example = "PadelPrixOurense"
    )
    private String name;


    @Schema(
            description = "Tax Identification Code (CIF) of the club",
            example = "A345F"
    )
    private String cif;

    @Schema(
            description = "Full postal address of the club",
            example = "Calle Vigo, 9. 12006, Castellón de la Plana, España"
    )
    private String address;


    @Schema(
            description = "Phone number of the club",
            example = "+34 966 123 456"
    )
    private String phone;

    @Schema(
            description = "Contact email of the club",
            example = "info@clubs.com"
    )
    private String email;

    @Schema(
            description = "Optional remarks or notes about the club",
            example = "Club prefers weekend tournaments only."
    )
    private String remark;

    @Schema(description = "ID used for accounting purposes", example = "ACC-4567")
    private String accountingId;

    private String micronId;

    @Schema(
            description = "Tenant to whom this club belongs",
            implementation = TenantEntity.class
    )
    private String tenantEntityName;

    @Schema(
            description = "Users manager associated with this club, if any",
            implementation = UserManagerEntity.class
    )
    private List<String> userManagers;


}
