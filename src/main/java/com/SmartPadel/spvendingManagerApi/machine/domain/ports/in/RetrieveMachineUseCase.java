package com.SmartPadel.spvendingManagerApi.machine.domain.ports.in;

import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RetrieveMachineUseCase {
    Page<Machine> getAllMachines(String search, Pageable pageable);
    Machine getMachineById(UUID machineId);
    List<Machine> findAllMachinesByClubId(UUID clubId);
}
