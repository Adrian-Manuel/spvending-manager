package com.smart_padel.spvending_management_api.club.infrastructure.dto;
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

import static com.smart_padel.spvending_management_api.shared.utils.RegexUtils.EMAIL_REGEX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@Schema(description = "DTO containing information about a club, including contact info and associated tenant.")
public class ClubDtoIn {
    @NotNull(message = "The name of the club is required")
    @Size(min = 1,max = 100, message = "must be at least 1 letter and cannot exceed 100 characters")
    @Schema(description = "Name of the club", example = "PadelPrixOurense")
    private String name;

    @NotNull(message = "The cif is required")
    @Schema(description = "Tax Identification Code (CIF) of the club", example = "A345F")
    private String cif;

    @Schema(description = "Full postal address of the club", example = "Calle Vigo, 9. 12006, Castellón de la Plana, España")
    private String address;

    @Schema(description = "Phone number of the club", example = "+34 966 123 456")
    private String phone;

    @Pattern(
            regexp = EMAIL_REGEX,
            message = "The email must be composed of a text string followed by @, another text string, a period and another text string, example: example@email.com"
    )
    @Schema(description = "Contact email of the club", example = "info@clubs.com")
    private String email;

    @Schema(description = "Optional remarks or notes about the club", example = "Club prefers weekend tournaments only.")
    private String remark;

    @Schema(description = "Micron ID associated with this club", example = "82160004")
    private String micronId;

    @NotNull(message = "Tenant Id is required")
    @Schema(description = "UUID of the associated tenant", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID tenantId;

    @NotNull(message = "AccountingId is required")
    @Schema(description = "ID used for accounting purposes", example = "ACC-4567")
    private String accountingId;
}
