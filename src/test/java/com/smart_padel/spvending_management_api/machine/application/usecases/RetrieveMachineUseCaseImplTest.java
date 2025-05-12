package com.smart_padel.spvending_management_api.machine.application.usecases;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.domain.ports.out.MachineRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class RetrieveMachineUseCaseImplTest {

    @Mock
    private MachineRepositoryPort machineRepositoryPort;

    @InjectMocks
    private RetrieveMachineUseCaseImpl retrieveMachineUseCase;

    @Test
    void getAllMachines_ValidSearchAndPageable_ReturnsPagedMachines() {
        String search = "test";
        Pageable pageable = Pageable.unpaged();
        Page<Machine> machinesPage = Page.empty();
        when(machineRepositoryPort.findAll(search, pageable)).thenReturn(machinesPage);
        Page<Machine> result = retrieveMachineUseCase.getAllMachines(search, pageable);
        assertThat(result).isEqualTo(machinesPage);
        verify(machineRepositoryPort).findAll(search, pageable);
    }

    @Test
    void getAllMachines_NullSearch_ReturnsPagedMachines() {
        Pageable pageable = Pageable.unpaged();
        Page<Machine> machinesPage = Page.empty();
        when(machineRepositoryPort.findAll(null, pageable)).thenReturn(machinesPage);
        Page<Machine> result = retrieveMachineUseCase.getAllMachines(null, pageable);
        assertThat(result).isEqualTo(machinesPage);
        verify(machineRepositoryPort).findAll(null, pageable);
    }

    @Test
    void getMachineById_ValidMachineId_ReturnsMachine() {
        UUID machineId = UUID.randomUUID();
        Machine machine = new Machine();
        when(machineRepositoryPort.findById(machineId)).thenReturn(machine);
        Machine result = retrieveMachineUseCase.getMachineById(machineId);
        assertThat(result).isEqualTo(machine);
        verify(machineRepositoryPort).findById(machineId);
    }

    @Test
    void findAllMachinesByClubId_ValidClubId_ReturnsMachinesList() {
        UUID clubId = UUID.randomUUID();
        List<Machine> machines = List.of(new Machine(), new Machine());
        when(machineRepositoryPort.findAllMachinesByClubId(clubId)).thenReturn(machines);
        List<Machine> result = retrieveMachineUseCase.findAllMachinesByClubId(clubId);
        assertThat(result).isEqualTo(machines);
        verify(machineRepositoryPort).findAllMachinesByClubId(clubId);
    }
}
