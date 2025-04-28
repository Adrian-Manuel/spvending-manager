package com.SmartPadel.spvendingManagerApi.tenant.infrastructure.dto;
import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
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
@Schema(description = "DTO containing a little information about a tenant")
public class TenantDtoOutDetail {
    @Schema(description = "Identificator of the tenant ")
    private UUID tenantId;
    @Schema(description = "Name of the tenant (company or organization)", example = "PadelPrix")
    private String name;
    @Schema(description = "CIF (Tax ID) of the tenant", example = "B12345678")
    private String cif;
    @Schema(description = "Full address of the tenant", example = "POLIGONO CIUDAD TRANSPORTE CALLE SUECIA, 9. 12006, CASTELLON DE LA PLANA. ESPAÑA.")
    private String address;
    @Schema(description = "Phone number of the tenant", example = "6094852")
    private String phone;
    @Schema(description = "Contact email address of the tenant", example = "info@padelprix.com")
    private String email;
    @Schema(description = "Additional notes or remarks about the tenant", example = "Empresa principal de la red de clubes.")
    private String remark;
    @Schema(description = "Micron ID associated with this tenant", example = "82160004")
    private String micronId;
    @Schema(description = "number of clubs owned by this tenant", implementation = Club.class)
    private List<String> managers;
}
