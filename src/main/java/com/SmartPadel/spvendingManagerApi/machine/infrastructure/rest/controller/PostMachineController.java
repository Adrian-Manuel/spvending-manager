package com.SmartPadel.spvendingManagerApi.machine.infrastructure.rest.controller;
import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.CreateMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoIn;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.mapper.MachineMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/machines")
@RequiredArgsConstructor
public class PostMachineController {
    private final CreateMachineUseCase createMachineUseCase;

    @PostMapping
    public ResponseEntity<MachineDtoOutPreview> createClub (@RequestBody @Valid MachineDtoIn machineDtoIn){
        Machine machineRequest= MachineMapper.toModel(machineDtoIn);
        machineRequest=createMachineUseCase.createMachine(machineDtoIn.getClubId(), machineRequest);
        return new ResponseEntity<>(MachineMapper.toDtoPreview(machineRequest), HttpStatus.CREATED);
    }
}
