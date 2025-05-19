package com.smart_padel.spvending_management_api.tenant.infrastructure.dto;
import java.util.UUID;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@Schema(description = "DTO containing information about a tenant, including contact info and club count.")
public class TenantDtoOutPreview {
    @Schema(
            description = "Unique identifier of the tenant",
            example = "d290f1ee-6c54-4b01-90e6-d701748f0851"
    )
    private UUID tenantId;
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
            description = "Phone number of the tenant",
            example = "6094852"
    )
    private String phone;
    @Schema(
            description = "Contact email address of the tenant",
            example = "info@padelprix.com"
    )
    private String email;
    @Schema(
            description = "Number of clubs owned by this tenant",
            example = "2"
    )
    private int clubsCount;

}
