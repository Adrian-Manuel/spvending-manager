package com.smart_padel.spvending_management_api.machine.application.usecases;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.CreateMachineUseCase;
import com.smart_padel.spvending_management_api.machine.domain.ports.out.MachineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CreateMachineUseCaseImpl implements CreateMachineUseCase {
   private final MachineRepositoryPort machineRepositoryPort;
    @Override
    public Machine createMachine(UUID clubId, Machine machine) {return machineRepositoryPort.save(clubId, machine);}
}
