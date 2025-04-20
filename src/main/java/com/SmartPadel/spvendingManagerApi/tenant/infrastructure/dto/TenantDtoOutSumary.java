package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto;

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
@Schema(description = "DTO containing information about tenants name and id")
public class TenantDtoOutSumary {
    @Schema(
            description = "Identificator of the tenant "
    )
    private UUID tenantId;


    @Schema(
            description = "Name of the tenant (company or organization)",
            example = "PadelPrix"
    )
    private String name;
}
