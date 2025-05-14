package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.UpdateClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoIn;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutDetail;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clubs")
public class PutClubController {
    private final UpdateClubUseCase updateClubUseCase;
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{clubId}")
    public ResponseEntity<ClubDtoOutDetail> updateClub(@PathVariable UUID clubId, @Valid @RequestBody ClubDtoIn clubDtoIn){
        Club clubRequest= ClubMapper.toModel(clubDtoIn);
        clubRequest=updateClubUseCase.updateClub(clubDtoIn.getTenantId(),clubId, clubRequest);
        return new ResponseEntity<>(ClubMapper.toDtoDetail(clubRequest), HttpStatus.OK);
    }

}
