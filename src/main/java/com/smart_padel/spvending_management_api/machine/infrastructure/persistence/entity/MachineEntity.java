package com.smart_padel.spvending_management_api.machine.infrastructure.persistence.entity;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.shared.utils.Filtrable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "machines")
public class MachineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID machineId;

    @Filtrable
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "micronId")
    private String micronId;

    @Filtrable
    @Column(name = "smartFridgeId",unique = true, nullable = false)
    private String smartFridgeId;

    @Column(name = "smartFridgPassword", nullable = false)
    private String smartFridgePassword;

    @Filtrable
    @Column(name = "terminalId",unique = true, nullable = false)
    @Schema(description = "Unique terminal ID used for payment or control systems", example = "14")
    private String terminalId;

    @Filtrable
    @Column(name = "toaSerialNumber", nullable = false, unique = true)
    @Schema(description = "Serial number assigned by TOA (provider or manufacturer)", example = "14455")
    private String toaSerialNumber;

    @Column(name = "rustdeskId",unique = true, nullable = false)
    @Schema(description = "ID used for remote control via RustDesk", example = "76")
    private String rustdeskId;

    @Column(name = "rustdeskPass", nullable = false)
    @Schema(description = "Password used for remote access through RustDesk", example = "Padelprix2025")
    private String rustdeskPass;

    @Filtrable(name = "club.name")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId", nullable = false, referencedColumnName = "clubId")
    @Schema(description = "The club where this machine is installed or managed", implementation = ClubEntity.class)
    private ClubEntity club;

    public static MachineEntity fromDomainModel(Machine machine){
        return MachineEntity.builder()
                .machineId(machine.getMachineId())
                .code(machine.getCode())
                .smartFridgeId(machine.getSmartFridgeId())
                .smartFridgePassword(machine.getSmartFridgePassword())
                .terminalId(machine.getTerminalId())
                .toaSerialNumber(machine.getToaSerialNumber())
                .rustdeskId(machine.getRustdeskId())
                .rustdeskPass(machine.getRustdeskPass())
                .micronId(machine.getMicronId())
                .build();
    }
    public Machine toDomainModel(){
        return  Machine.builder()
                .machineId(machineId)
                .code(code)
                .smartFridgeId(smartFridgeId)
                .smartFridgePassword(smartFridgePassword)
                .terminalId(terminalId)
                .toaSerialNumber(toaSerialNumber)
                .rustdeskId(rustdeskId)
                .rustdeskPass(rustdeskPass)
                .clubName(club.getName())
                .clubId(club.getClubId())
                .micronId(micronId)
                .build();
    }

}
