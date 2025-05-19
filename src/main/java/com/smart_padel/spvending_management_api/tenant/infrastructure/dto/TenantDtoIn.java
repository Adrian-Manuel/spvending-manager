package com.smart_padel.spvending_management_api.tenant.infrastructure.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.smart_padel.spvending_management_api.shared.utils.RegexUtils.EMAIL_REGEX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@Schema(description = "DTO for input data when creating or updating a tenant, including contact information.")
public class TenantDtoIn {
    @NotNull(message = "The name of the tenant is required")
    @Size(min = 1, max = 100, message = "must be at least 1 letter and cannot exceed 100 characters")
    @Schema(
            description = "Name of the tenant (company or organization)",
            example = "PadelPrix"
    )
    private String name;

    @Schema(
            description = "CIF (Tax ID) of the tenant",
            example = "B12345678"
    )
    private String cif;

    @Schema(
            description = "Full address of the tenant",
            example = "POLIGONO CIUDAD TRANSPORTE CALLE SUECIA, 9. 12006, CASTELLON DE LA PLANA. ESPAÑA."
    )
    private String address;

    @Schema(
            description = "Phone number of the tenant",
            example = "6094852"
    )
    private String phone;
    @Pattern(
            regexp = EMAIL_REGEX,
            message = "The email must be composed of a text string followed by @, another text string, a period and another text string, example: example@email.com"
    )
    @Schema(
            description = "Contact email address of the tenant",
            example = "info@padelprix.com"
    )
    private String email;

    @Size(
            min = 0, max = 255,
            message = "Remark cannot exceed 255 characters"
    )
    @Schema(
            description = "Additional notes or remarks about the tenant",
            example = "Main company in the club network."
    )
    private String remark;

    @Schema(
            description = "Micron ID associated with this tenant",
            example = "82160004"
    )
    private String micronId;
}
