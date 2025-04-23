package com.SmartPadel.spvendingManagerApi.machine.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.DeleteMachineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/machines")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeleteMachineController {

    private final DeleteMachineUseCase deleteMachineUseCase;

    @DeleteMapping("/{machineId}")
    public ResponseEntity<Void> deleteMachine(@PathVariable UUID machineId){
        deleteMachineUseCase.deleteMachine(machineId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
