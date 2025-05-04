package com.smart_padel.spvending_management_api.machine.infrastructure.utils;
import com.smart_padel.spvending_management_api.machine.infrastructure.persistence.repository.JpaMachineRepository;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceNotFoundException;
import java.util.UUID;
public class MachineHelperAdapter {
    private MachineHelperAdapter() {
        throw new IllegalStateException("Helper class");
    }
    public static void validateMachineExists(JpaMachineRepository repo, UUID machineId){
        if(!repo.existsById(machineId)){
            throw new ResourceNotFoundException("The machine does not exist");
        }
    }
}
