package com.SmartPadel.spvendingManagerApi.machine.infrastructure.utils;

import com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.entity.MachineEntity;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.persistence.repository.JpaMachineRepository;
import com.SmartPadel.spvendingManagerApi.shared.Exceptions.ResourceNotFoundException;

import java.util.UUID;

public class MachineHelperAdapter {
    public static MachineEntity getMachineOrThrow(JpaMachineRepository repo, UUID machineId){
        return repo.findById(machineId).orElseThrow(()->new ResourceNotFoundException("The requested machine was not found"));
    }

    public static void validateMachineExists(JpaMachineRepository repo, UUID machineId){
        if(!repo.existsById(machineId)){
            throw new ResourceNotFoundException("The machine does not exist");
        }
    }
}
