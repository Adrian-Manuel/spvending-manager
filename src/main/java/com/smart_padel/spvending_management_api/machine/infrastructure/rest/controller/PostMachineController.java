package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.CreateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoIn;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutPreview;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/v1/machines")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostMachineController {
    @Value("${app.AESecret_key}")
    private String aeSecretKey;
    private final CreateMachineUseCase createMachineUseCase;
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<MachineDtoOutPreview> createClub (@RequestBody @Valid MachineDtoIn machineDtoIn) throws GeneralSecurityException {
        Machine machineRequest= MachineMapper.toModel(machineDtoIn, aeSecretKey);
        machineRequest=createMachineUseCase.createMachine(machineDtoIn.getClubId(), machineRequest);
        return new ResponseEntity<>(MachineMapper.toDtoPreview(machineRequest), HttpStatus.CREATED);
    }
}
