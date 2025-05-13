package com.smart_padel.spvending_management_api.machine.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.DeleteMachineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
@RequestMapping("/api/v1/machines")
@RestController
@RequiredArgsConstructor
public class DeleteMachineController {
    private final DeleteMachineUseCase deleteMachineUseCase;
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{machineId}")
    public ResponseEntity<Void> deleteMachine(@PathVariable UUID machineId){
        deleteMachineUseCase.deleteMachine(machineId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
