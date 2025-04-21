package com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.mapper;
import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoIn;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.shared.Utils.AESGCMEncryption;


public class MachineMapper {
    public static MachineDtoOutPreview toDtoPreview(Machine machine){
        return MachineDtoOutPreview.builder()
                .machineId(machine.getMachineId())
                .code(machine.getCode())
                .clubName(machine.getClub().getName())
                .smartFridgeId(machine.getSmartFridgeId())
                .terminalId(machine.getTerminalId())
                .toSerialNumber(machine.getToaSerialNumber())
                .build();
    }

    public static MachineDtoOutDetail toDtoDetail (Machine machine) throws Exception {
        return MachineDtoOutDetail.builder()
                .machineId(machine.getMachineId())
                .code(machine.getCode())
                .clubName(machine.getClub().getName())
                .smartFridgeId(machine.getSmartFridgeId())
                .terminalId(machine.getTerminalId())
                .toaSerialNumber(machine.getToaSerialNumber())
                .rustdeskId(machine.getRustdeskId())
                .rustdeskPass(AESGCMEncryption.decrypt(machine.getRustdeskPass()))
                .smartFridgePassword(AESGCMEncryption.decrypt(machine.getSmartFridgePassword()))
                .build();
    }

    public static Machine toModel(MachineDtoIn machineDtoIn) throws Exception {
        return Machine.builder()
                .code(machineDtoIn.getCode())
                .smartFridgeId(machineDtoIn.getSmartFridgeId())
                .terminalId(machineDtoIn.getTerminalId())
                .toaSerialNumber(machineDtoIn.getToaSerialNumber())
                .rustdeskId(machineDtoIn.getRustdeskId())
                .rustdeskPass(AESGCMEncryption.encrypt(machineDtoIn.getRustdeskPass()))
                .smartFridgePassword(AESGCMEncryption.encrypt(machineDtoIn.getSmartFridgePassword()))
                .build();
    }
}
