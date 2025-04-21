package com.SmartPadel.spvendingManagerApi.machine.application.usecases;

import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.RetrieveMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.out.MachineRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveMachineUseCaseImpl implements RetrieveMachineUseCase {
    private final MachineRepositoryPort machineRepositoryPort;
}
