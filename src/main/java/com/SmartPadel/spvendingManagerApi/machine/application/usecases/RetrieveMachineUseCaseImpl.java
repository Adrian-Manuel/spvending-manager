package com.SmartPadel.spvendingManagerApi.machine.application.usecases;

import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.RetrieveMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.out.MachineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveMachineUseCaseImpl implements RetrieveMachineUseCase {
    private final MachineRepositoryPort machineRepositoryPort;

    @Override
    public Page<Machine> getAllMachines(String search, Pageable pageable) {
        return machineRepositoryPort.findAll(search, pageable);
    }

    @Override
    public Machine findById(UUID machineId) {
        return machineRepositoryPort.findById(machineId);
    }

    @Override
    public List<Machine> findAllMachinesByClubId(UUID clubId) {
        return machineRepositoryPort.findAllMachinesByClubId(clubId);
    }
}
