package com.smart_padel.spvending_management_api.machine.application.usecases;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.out.MachineRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMachineUseCaseImplTest {

    @Mock
    private MachineRepositoryPort machineRepositoryPort;

    @InjectMocks
    private UpdateMachineUseCaseImpl updateMachineUseCase;

    @Test
    void updateMachine_ValidInputs_ReturnsUpdatedMachine() {
        UUID clubId = UUID.randomUUID();
        UUID machineId = UUID.randomUUID();
        Machine machine = new Machine();
        Machine updatedMachine = new Machine();
        when(machineRepositoryPort.update(clubId, machineId, machine)).thenReturn(updatedMachine);
        Machine result = updateMachineUseCase.updateMachine(clubId, machineId, machine);
        assertThat(result).isEqualTo(updatedMachine);
        verify(machineRepositoryPort).update(clubId, machineId, machine);
    }

}
