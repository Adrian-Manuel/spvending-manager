package com.SmartPadel.spvendingManagerApi.machine.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.UpdateMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoIn;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.mapper.MachineMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/machines")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PutMachineController {
    private final UpdateMachineUseCase updateMachineUseCase;
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{machineId}")
    public ResponseEntity<MachineDtoOutDetail> updateMachine(@PathVariable UUID machineId, @Valid @RequestBody MachineDtoIn machineDtoIn) throws Exception {
        Machine machineRequest= MachineMapper.toModel(machineDtoIn);
        machineRequest=updateMachineUseCase.updateMachine(machineDtoIn.getClubId(),machineId, machineRequest);
        return new ResponseEntity<>(MachineMapper.toDtoDetail(machineRequest), HttpStatus.OK);
    }
}
