package com.smart_padel.spvending_management_api.machine.application.usecases;
import com.smart_padel.spvending_management_api.machine.domain.ports.out.MachineRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteMachineUseCaseImplTest {

    @Mock
    private MachineRepositoryPort machineRepositoryPort;

    @InjectMocks
    private DeleteMachineUseCaseImpl deleteMachineUseCase;

    @Test
    void deleteMachine_ValidMachineId_DeletesMachine() {
        UUID machineId = UUID.randomUUID();
        deleteMachineUseCase.deleteMachine(machineId);
        verify(machineRepositoryPort).deleteById(machineId);
    }
}