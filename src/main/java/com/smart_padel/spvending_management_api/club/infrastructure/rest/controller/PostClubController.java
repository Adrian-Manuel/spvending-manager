package com.smart_padel.spvending_management_api.club.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.club.domain.model.Club;
import com.smart_padel.spvending_management_api.club.domain.ports.in.CreateClubUseCase;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoIn;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.ClubDtoOutPreview;
import com.smart_padel.spvending_management_api.club.infrastructure.dto.mapper.ClubMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
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
