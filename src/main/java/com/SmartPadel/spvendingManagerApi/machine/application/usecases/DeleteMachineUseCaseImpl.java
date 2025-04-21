package com.SmartPadel.spvendingManagerApi.machine.application.usecases;

import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.DeleteMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.out.MachineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteMachineUseCaseImpl implements DeleteMachineUseCase {
    private final MachineRepositoryPort machineRepositoryPort;

    @Override
    public void deleteMachine(UUID machineId) {
        machineRepositoryPort.deleteById(machineId);
    }
}
