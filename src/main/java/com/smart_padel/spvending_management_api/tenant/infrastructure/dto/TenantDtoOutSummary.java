package com.smart_padel.spvending_management_api.tenant.infrastructure.dto;
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
@Schema(description = "DTO containing summary information about a tenant, including its name and unique identifier.")
public class TenantDtoOutSummary {
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
}
