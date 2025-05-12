package com.smart_padel.spvending_management_api.machine.infrastructure.utils;

import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.repository.JpaMachineRepository;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MachineHelperAdapterTest {

    @Mock
    private JpaMachineRepository jpaMachineRepository;

    @Test
    void validateMachineExists_ExistingMachine_DoesNotThrowException() {
        UUID machineId = UUID.randomUUID();
        when(jpaMachineRepository.existsById(machineId)).thenReturn(true);

        assertDoesNotThrow(() -> MachineHelperAdapter.validateMachineExists(jpaMachineRepository, machineId));
        verify(jpaMachineRepository).existsById(machineId);
    }

    @Test
    void validateMachineExists_NonExistingMachine_ThrowsResourceNotFoundException() {
        UUID machineId = UUID.randomUUID();
        when(jpaMachineRepository.existsById(machineId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () ->
                MachineHelperAdapter.validateMachineExists(jpaMachineRepository, machineId));
        verify(jpaMachineRepository).existsById(machineId);
    }

    @Test
    void validateMachineExists_NullMachineId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                MachineHelperAdapter.validateMachineExists(jpaMachineRepository, null));
        verifyNoInteractions(jpaMachineRepository);
    }

    @Test
    void privateConstructor_ThrowsIllegalStateException() throws NoSuchMethodException {
        var constructor = MachineHelperAdapter.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        assertThat(exception.getCause()).isInstanceOf(IllegalStateException.class);
    }
}

