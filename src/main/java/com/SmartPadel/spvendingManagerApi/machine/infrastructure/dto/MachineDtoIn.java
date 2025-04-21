package com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "DTO that contains the input information of a Machine entity, including credentials and assigned club")
public class MachineDtoIn {


    @NotNull(message = "The code of the machine is required")
    @Schema(
            description = "Unique internal code of the machine",
            example = "2341"
    )
    private String code;

    @NotNull(message = "The Smart Fridge ID of the machine is required")
    @Schema(
            description = "ID assigned to the smart fridge module of the machine",
            example = "12"
    )
    private String smartFridgeId;

    @NotNull(message = "The Smart Fridge password of the machine is required")
    @Schema(
            description = "Password to access the smart fridge",
            example = "abc123."
    )
    private String smartFridgePassword;

    @NotNull(message = "The Terminal ID of the machine is required")
    @Schema(
            description = "Unique terminal ID used for payment or control systems",
            example = "14"
    )
    private String terminalId;

    @NotNull(message = "The TOA serial number of the machine is required")
    @Schema(
            description = "Serial number assigned by TOA (provider or manufacturer)",
            example = "14455"
    )
    private String toaSerialNumber;

    @NotNull(message = "The RustDesk ID of the machine is required")
    @Schema(
            description = "ID used for remote control via RustDesk",
            example = "76"
    )
    private String rustdeskId;

    @NotNull(message = "The RustDesk password of the machine is required")
    @Schema(
            description = "Password used for remote access through RustDesk",
            example = "Padelprix2025"
    )
    private String rustdeskPass;

    @NotNull(message = "You must register a club to register this machine.")
    @Schema(
            description = "The club where this machine is installed or managed",
            implementation = ClubEntity.class
    )
    private UUID clubId;
}
