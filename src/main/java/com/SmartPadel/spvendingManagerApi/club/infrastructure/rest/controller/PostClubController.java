package com.SmartPadel.spvendingManagerApi.club.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.CreateClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoIn;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.mapper.ClubMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PostClubController {
   private final CreateClubUseCase createClubUseCase;
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<ClubDtoOutPreview> createClub (@RequestBody @Valid ClubDtoIn clubDtoIn){
        Club clubRequest= ClubMapper.toModel(clubDtoIn);
        clubRequest=createClubUseCase.createClub(clubDtoIn.getTenantId(), clubRequest);
        return new ResponseEntity<>(ClubMapper.toDtoPreview(clubRequest), HttpStatus.CREATED);
    }
}
