package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.RetrieveClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutDetail;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutPreview;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutSummary;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import com.smart_padel.spvending_management_api.machine.domain.ports.in.RetrieveMachineUseCase;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.MachineDtoOutPreview;
import com.smart_padel.spvending_management_api.machine.infrastructure.dto.mapper.MachineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class GetClubController {
    private final RetrieveClubUseCase retrieveClubUseCase;
    private final RetrieveMachineUseCase retrieveMachineUseCase;
    private final MachineMapper machineMapper;
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity<Page<ClubDtoOutPreview>> getAllClubs(@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClubDtoOutPreview> clubs = retrieveClubUseCase.getAllClubs(search, pageable).map(ClubMapper::toDtoPreview);
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDtoOutDetail> getClubById(@PathVariable UUID clubId){
        Club clubRequest=retrieveClubUseCase.getClubById(clubId);
        return new ResponseEntity<>(ClubMapper.toDtoDetail(clubRequest), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{clubId}/machines")
    public ResponseEntity<List<MachineDtoOutPreview>> getAllMachinesByClub(@PathVariable UUID clubId){
        List<MachineDtoOutPreview> machines= retrieveMachineUseCase.findAllMachinesByClubId(clubId).stream().map(machineMapper::toDtoPreview).toList();
        return new ResponseEntity<>(machines, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/all-summary")
    public ResponseEntity<List<ClubDtoOutSummary>> getClubsSummary(){
        List<ClubDtoOutSummary> tenantsSummary=retrieveClubUseCase.getAllClubsSummary().stream().map(ClubMapper::toDtoSummary).toList();
        return new ResponseEntity<>(tenantsSummary, HttpStatus.OK);
    }
}

