package com.smart_padel.spvending_management_api.machine.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Machine {
    private UUID machineId;
    private String code;
    private String micronId;
    private String smartFridgeId;
    private String smartFridgePassword;
    private String terminalId;
    private String toaSerialNumber;
    private String rustdeskId;
    private String rustdeskPass;
    private String clubName;
}
