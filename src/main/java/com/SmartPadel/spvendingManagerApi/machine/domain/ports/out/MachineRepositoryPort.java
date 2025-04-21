package com.SmartPadel.spvendingManagerApi.machine.domain.ports.out;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MachineRepositoryPort {
    Page<Machine> findAll(String search, Pageable pageable);
    Machine save(UUID clubId , Machine machine);
    Machine findById(UUID machineId);
    Machine update(UUID clubId,UUID machineId,Machine machine);
    void deleteById(UUID machineId);
    List<Machine> findAllMachinesByClubId(UUID clubId);
}
