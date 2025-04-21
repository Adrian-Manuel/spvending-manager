package com.SmartPadel.spvendingManagerApi.machine.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;

import java.util.UUID;

public interface CreateMachineUseCase {
    Machine createMachine(UUID clubId, Machine machine);
}
