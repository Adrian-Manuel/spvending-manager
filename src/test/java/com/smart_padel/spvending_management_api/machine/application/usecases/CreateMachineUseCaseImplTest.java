package com.smart_padel.spvending_management_api.machine.application.usecases;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.out.MachineRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CreateMachineUseCaseImplTest {

    @Mock
    private MachineRepositoryPort machineRepositoryPort;

    @InjectMocks
    private CreateMachineUseCaseImpl createMachineUseCase;

    @Test
    void createMachine_ValidInput_ReturnsSavedMachine() {
        UUID clubId = UUID.randomUUID();
        Machine machine = new Machine();
        Machine savedMachine = new Machine();
        when(machineRepositoryPort.save(clubId, machine)).thenReturn(savedMachine);
        Machine result = createMachineUseCase.createMachine(clubId, machine);
        assertThat(result).isEqualTo(savedMachine);
        verify(machineRepositoryPort).save(clubId, machine);
    }
}
