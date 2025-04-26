package com.SmartPadel.spvendingManagerApi.club.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.club.application.usecases.UpdateClubUseCaseImpl;
import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoIn;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.mapper.ClubMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/clubs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PutClubController {
    private final UpdateClubUseCaseImpl updateClubUseCase;
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{clubId}")
    public ResponseEntity<ClubDtoOutDetail> updateClub(@PathVariable UUID clubId, @Valid @RequestBody ClubDtoIn clubDtoIn){
        Club clubRequest= ClubMapper.toModel(clubDtoIn);
        clubRequest=updateClubUseCase.updateClub(clubDtoIn.getTenantId(),clubId, clubRequest);
        return new ResponseEntity<>(ClubMapper.toDtoDetail(clubRequest), HttpStatus.OK);
    }

}
