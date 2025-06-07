package com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutDetail;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutPreview;
import com.smart_padel.spvending_management_api.shared.utils.AESGCMEncryption;

import java.security.GeneralSecurityException;
public class MachineMapper {
    private MachineMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static MachineDtoOutPreview toDtoPreview(Machine machine){
        return MachineDtoOutPreview.builder()
                .machineId(machine.getMachineId())
                .code(machine.getCode())
                .clubName(machine.getClubName())
                .smartFridgeId(machine.getSmartFridgeId())
                .terminalId(machine.getTerminalId())
                .toSerialNumber(machine.getToaSerialNumber())
                .build();
    }

    public static MachineDtoOutDetail toDtoDetail (Machine machine,String aeSecretKey) throws GeneralSecurityException {
        return MachineDtoOutDetail.builder()
                .machineId(machine.getMachineId())
                .code(machine.getCode())
                .micronId(machine.getMicronId())
                .clubName(machine.getClubName())
                .clubId(machine.getClubId())
                .smartFridgeId(machine.getSmartFridgeId())
                .terminalId(machine.getTerminalId())
                .toaSerialNumber(machine.getToaSerialNumber())
                .rustdeskId(machine.getRustdeskId())
                .rustdeskPass(AESGCMEncryption.decrypt(machine.getRustdeskPass(), aeSecretKey))
                .smartFridgePassword(AESGCMEncryption.decrypt(machine.getSmartFridgePassword(), aeSecretKey))
                .build();
    }

    public static Machine toModel(MachineDtoIn machineDtoIn, String aeSecretKey) throws GeneralSecurityException {
        return Machine.builder()
                .code(machineDtoIn.getCode())
                .micronId(machineDtoIn.getMicronId())
                .smartFridgeId(machineDtoIn.getSmartFridgeId())
                .terminalId(machineDtoIn.getTerminalId())
                .toaSerialNumber(machineDtoIn.getToaSerialNumber())
                .rustdeskId(machineDtoIn.getRustdeskId())
                .rustdeskPass(AESGCMEncryption.encrypt(machineDtoIn.getRustdeskPass(), aeSecretKey))
                .smartFridgePassword(AESGCMEncryption.encrypt(machineDtoIn.getSmartFridgePassword(), aeSecretKey))
                .build();
    }
}
