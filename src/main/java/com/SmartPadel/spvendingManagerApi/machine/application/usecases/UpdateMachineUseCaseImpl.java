package com.SmartPadel.spvendingManagerApi.machine.application.usecases;

import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.UpdateMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.out.MachineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateMachineUseCaseImpl implements UpdateMachineUseCase {
    private final MachineRepositoryPort machineRepositoryPort;

    @Override
    public Machine updateMachine(UUID clubId, UUID machineId, Machine machine) {
        return machineRepositoryPort.update(clubId, machineId, machine);
    }
}
