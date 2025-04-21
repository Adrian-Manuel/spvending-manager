package com.SmartPadel.spvendingManagerApi.machine.application.usecases;

import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.CreateMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.out.MachineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateMachineUseCaseImpl implements CreateMachineUseCase {
   private final MachineRepositoryPort machineRepositoryPort;

    @Override
    public Machine createMachine(UUID clubId, Machine machine) {
        return machineRepositoryPort.save(clubId, machine);
    }
}
