package com.SmartPadel.spvendingManagerApi.machine.infrastructure.rest.controller;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.DeleteMachineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RequestMapping("/api/v1/machines")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeleteMachineController {
    private final DeleteMachineUseCase deleteMachineUseCase;
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{machineId}")
    public ResponseEntity<Void> deleteMachine(@PathVariable UUID machineId){
        deleteMachineUseCase.deleteMachine(machineId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
