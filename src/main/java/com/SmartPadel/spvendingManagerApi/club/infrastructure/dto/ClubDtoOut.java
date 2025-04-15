package com.SmartPadel.spvendingManagerApi.club.infrastructure.dto;

import com.SmartPadel.spvendingManagerApi.externalUser.model.UserManager;
import com.SmartPadel.spvendingManagerApi.machines.modelsV1.Machine;
import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.TenantEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ClubDtoOut {

    @Schema(
            description = "id of the club",
            example = "1231343254"
    )
    private UUID clubId;

    @NotNull(message = "The name of the club is required")
    @Size(max = 100, message = "The number of characters cannot exceed 100")
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


    @Email(message = "Invalid email")
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

    @NotNull(message = "accountingId is required")
    @Schema(description = "ID used for accounting purposes", example = "ACC-4567")
    private String accountingId;

    @NotNull(message = "You must register a tenant before you can register this club.")
    @Schema(
            description = "Tenant to whom this club belongs",
            implementation = TenantEntity.class
    )
    private TenantEntity tenantEntity;

    @Schema(
            description = "List of vending or management machines associated with this club",
            implementation = Machine.class
    )
    private List<Machine> machines;

    @Schema(
            description = "Users manager associated with this club, if any",
            implementation = UserManager.class
    )
    private List<UserManager> userManagers;
    @Schema(
            description = "number of machines owned by this club",
            implementation = UserManager.class
    )
    private String machinesCount;
}
