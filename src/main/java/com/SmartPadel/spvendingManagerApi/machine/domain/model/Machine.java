package com.SmartPadel.spvendingManagerApi.machine.domain.model;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.persistance.entity.ClubEntity;
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
    private String smartFridgeId;
    private String smartFridgePassword;
    private String terminalId;
    private String toaSerialNumber;
    private String rustdeskId;
    private String rustdeskPass;
    private ClubEntity club;
}
