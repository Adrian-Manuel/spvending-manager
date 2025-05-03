package com.smart_padel.spvending_management_api.machine.application.usecases;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.UpdateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.domain.ports.out.MachineRepositoryPort;
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
