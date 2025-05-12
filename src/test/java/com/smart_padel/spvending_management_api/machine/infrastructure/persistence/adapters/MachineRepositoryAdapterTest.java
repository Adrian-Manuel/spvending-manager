
package com.smart_padel.spvending_management_api.machine.infrastructure.persistence.adapters;

import com.smart_padel.spvending_management_api.club.infrastructure.persistance.entity.ClubEntity;
import com.smart_padel.spvending_management_api.club.infrastructure.persistance.repository.JpaClubRepository;
import com.smart_padel.spvending_management_api.machine.domain.model.Machine;
import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.entity.MachineEntity;
import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.repository.JpaMachineRepository;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MachineRepositoryAdapterTest {

    @Mock private JpaMachineRepository jpaMachineRepository;
    @Mock private JpaClubRepository jpaClubRepository;
    @InjectMocks private MachineRepositoryAdapter adapter;

    private UUID clubId, machineId;
    private ClubEntity clubEntity;
    private Machine machine;
    private MachineEntity machineEntity;
    private Pageable pageable;

    @BeforeEach
    void setup() {
        clubId = UUID.randomUUID();
        machineId = UUID.randomUUID();
        clubEntity = ClubEntity.builder().clubId(clubId).name("Test Club").build();
        machine = Machine.builder().machineId(machineId).code("M-001").clubName(clubEntity.getName()).build();
        machineEntity = MachineEntity.fromDomainModel(machine);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findAll_returnsPageOfMachines() {
        machineEntity.setClub(clubEntity);
        Page<MachineEntity> entityPage = new PageImpl<>(List.of(machineEntity), pageable, 1);
        when(jpaMachineRepository.findAll(ArgumentMatchers.<Specification<MachineEntity>>any(), eq(pageable)))
                .thenReturn(entityPage);

        Page<Machine> result = adapter.findAll("code:M-001", pageable);
        assertThat(result.getContent()).hasSize(1);
        verify(jpaMachineRepository).findAll(ArgumentMatchers.<Specification<MachineEntity>>any(), eq(pageable));
    }

    @Test
    void save_returnsSavedMachine() {
        when(jpaClubRepository.findById(clubId)).thenReturn(Optional.of(clubEntity));
        machineEntity.setClub(clubEntity);
        when(jpaMachineRepository.save(any())).thenReturn(machineEntity);
        Machine saved = adapter.save(clubId, machine);
        assertThat(saved.getMachineId()).isEqualTo(machineId);
        verify(jpaClubRepository).findById(clubId);
        verify(jpaMachineRepository).save(any());
    }

    @Test
    void findById_returnsMachineOrThrows() {
        machineEntity.setClub(clubEntity);
        when(jpaMachineRepository.findById(machineId)).thenReturn(Optional.of(machineEntity));
        assertThat(adapter.findById(machineId).getMachineId()).isEqualTo(machineId);
        when(jpaMachineRepository.findById(machineId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> adapter.findById(machineId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no machine with that id");
    }

    @Test
    void update_modifiesAndReturnsUpdatedMachine() {
        when(jpaMachineRepository.existsById(machineId)).thenReturn(true);
        when(jpaClubRepository.findById(clubId)).thenReturn(Optional.of(clubEntity));
        when(jpaMachineRepository.save(any())).thenAnswer(invocation -> {
            MachineEntity entity = invocation.getArgument(0);
            entity.setClub(clubEntity);
            return entity;
        });
        Machine updated = adapter.update(clubId, machineId, machine);
        assertThat(updated.getMachineId()).isEqualTo(machineId);
        verify(jpaMachineRepository).save(any());
    }

    @Test
    void deleteById_deletesIfExistsOrThrows() {
        when(jpaMachineRepository.existsById(machineId)).thenReturn(true);
        doNothing().when(jpaMachineRepository).deleteById(machineId);
        adapter.deleteById(machineId);
        verify(jpaMachineRepository).deleteById(machineId);
        when(jpaMachineRepository.existsById(machineId)).thenReturn(false);
        assertThatThrownBy(() -> adapter.deleteById(machineId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("The machine does not exist");
    }

    @Test
    void findAllMachinesByClubId_returnsList() {
        when(jpaClubRepository.existsById(clubId)).thenReturn(true);
        machineEntity.setClub(clubEntity);
        when(jpaMachineRepository.findAll(ArgumentMatchers.<Specification<MachineEntity>>any()))
                .thenReturn(List.of(machineEntity));
        List<Machine> result = adapter.findAllMachinesByClubId(clubId);
        assertThat(result).hasSize(1);
        verify(jpaMachineRepository).findAll(ArgumentMatchers.<Specification<MachineEntity>>any());
        verify(jpaClubRepository).existsById(clubId);
    }
}