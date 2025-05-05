package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.UpdateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutDetail;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/machines")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PutMachineController {
    private final UpdateMachineUseCase updateMachineUseCase;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{machineId}")
    public ResponseEntity<MachineDtoOutDetail> updateMachine(@PathVariable UUID machineId, @Valid @RequestBody MachineDtoIn machineDtoIn) throws GeneralSecurityException {
        Machine machineRequest= MachineMapper.toModel(machineDtoIn, aeSecretKey);
        machineRequest=updateMachineUseCase.updateMachine(machineDtoIn.getClubId(),machineId, machineRequest);
        return new ResponseEntity<>(MachineMapper.toDtoDetail(machineRequest, aeSecretKey), HttpStatus.OK);
    }
}
