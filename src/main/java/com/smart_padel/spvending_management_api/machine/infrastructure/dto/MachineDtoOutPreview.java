package com.smart_padel.spvending_management_api.machine.infrastructure.dto;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@Schema(description = "DTO that contains the preview information of a Machine entity, including credentials and assigned club")
public class MachineDtoOutPreview {
    @Schema(description = "Unique ID of the machine in the database", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private  UUID machineId;

    @Schema(description = "Unique internal code of the machine", example = "2341")
    private String code;

    @Schema(description = "ID assigned to the smart fridge module of the machine", example = "12")
    private String smartFridgeId;

    @Schema(description = "Unique terminal ID used for payment or control systems", example = "14")
    private String terminalId;

    @Schema(description = "Serial number assigned by TOA (provider or manufacturer)", example = "14455")
    private String toSerialNumber;

    @Schema(description = "Name of the club where this machine is installed or managed", example = "PadelPrix Ourense")
    private String clubName;
}
