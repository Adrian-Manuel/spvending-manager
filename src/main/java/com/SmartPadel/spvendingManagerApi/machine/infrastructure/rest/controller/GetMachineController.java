package com.SmartPadel.spvendingManagerApi.machine.infrastructure.rest.controller;
import com.SmartPadel.spvendingManagerApi.machine.domain.model.Machine;
import com.SmartPadel.spvendingManagerApi.machine.domain.ports.in.RetrieveMachineUseCase;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.MachineDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.machine.infrastructure.dto.mapper.MachineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/machines")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GetMachineController {
    private final RetrieveMachineUseCase retrieveMachineUseCase;
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity<Page<MachineDtoOutPreview>> getAllClubs(@RequestParam(required = false) String search,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MachineDtoOutPreview> machines = retrieveMachineUseCase.getAllMachines(search, pageable).map(MachineMapper::toDtoPreview);
        return new ResponseEntity<>(machines, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{machineId}")
    public ResponseEntity<MachineDtoOutDetail> getClubById(@PathVariable UUID machineId) throws Exception {
        Machine machineRequest=retrieveMachineUseCase.getMachineById(machineId);
        return new ResponseEntity<>(MachineMapper.toDtoDetail(machineRequest), HttpStatus.OK);
    }
}
