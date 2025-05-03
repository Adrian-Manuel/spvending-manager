package com.smart_padel.spvending_management_api.machine.domain.ports.in;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import java.util.UUID;
public interface UpdateMachineUseCase {Machine updateMachine(UUID clubId, UUID machineId, Machine machine);}
