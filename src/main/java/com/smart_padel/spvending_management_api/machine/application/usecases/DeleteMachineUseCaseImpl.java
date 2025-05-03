package com.smart_padel.spvending_management_api.machine.application.usecases;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.DeleteMachineUseCase;
import com.smart_padel.spvending_management_api.machine.domain.ports.out.MachineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DeleteMachineUseCaseImpl implements DeleteMachineUseCase {
    private final MachineRepositoryPort machineRepositoryPort;
    @Override
    public void deleteMachine(UUID machineId) {machineRepositoryPort.deleteById(machineId);}
}
