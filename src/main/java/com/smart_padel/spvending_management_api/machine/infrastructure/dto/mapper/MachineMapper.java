package com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutDetail;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutPreview;
import com.smart_padel.spvending_management_api.shared.utils.AESGCMEncryption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MachineMapper {

     private final AESGCMEncryption aesgcmEncryption;

    public MachineDtoOutPreview toDtoPreview(Machine machine){
        return MachineDtoOutPreview.builder()
                .machineId(machine.getMachineId())
                .code(machine.getCode())
                .clubName(machine.getClub().getName())
                .smartFridgeId(machine.getSmartFridgeId())
                .terminalId(machine.getTerminalId())
                .toSerialNumber(machine.getToaSerialNumber())
                .build();
    }

    public MachineDtoOutDetail toDtoDetail (Machine machine) throws Exception {
        return MachineDtoOutDetail.builder()
                .machineId(machine.getMachineId())
                .code(machine.getCode())
                .clubName(machine.getClub().getName())
                .smartFridgeId(machine.getSmartFridgeId())
                .terminalId(machine.getTerminalId())
                .toaSerialNumber(machine.getToaSerialNumber())
                .rustdeskId(machine.getRustdeskId())
                .rustdeskPass(aesgcmEncryption.decrypt(machine.getRustdeskPass()))
                .smartFridgePassword(aesgcmEncryption.decrypt(machine.getSmartFridgePassword()))
                .build();
    }

    public Machine toModel(MachineDtoIn machineDtoIn) throws Exception {
        return Machine.builder()
                .code(machineDtoIn.getCode())
                .smartFridgeId(machineDtoIn.getSmartFridgeId())
                .terminalId(machineDtoIn.getTerminalId())
                .toaSerialNumber(machineDtoIn.getToaSerialNumber())
                .rustdeskId(machineDtoIn.getRustdeskId())
                .rustdeskPass(aesgcmEncryption.encrypt(machineDtoIn.getRustdeskPass()))
                .smartFridgePassword(aesgcmEncryption.encrypt(machineDtoIn.getSmartFridgePassword()))
                .build();
    }
}
