package com.SmartPadel.spvendingManagerApi.club.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.club.domain.model.Club;
import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.RetrieveClubUseCase;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.ClubDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.mapper.ClubMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
public class GetClubController {
    private final RetrieveClubUseCase retrieveClubUseCase;

    @GetMapping
    public ResponseEntity<Page<ClubDtoOutPreview>> getAllClubs(@RequestParam(required = false) String search,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClubDtoOutPreview> clubs = (search != null)
                ? retrieveClubUseCase.getAllClubs(search, pageable).map(ClubMapper::toDtoPreview)
                : retrieveClubUseCase.getAllClubs(pageable).map(ClubMapper::toDtoPreview);;
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDtoOutDetail> getClubById(@PathVariable UUID clubId){
        Club clubRequest=retrieveClubUseCase.getClubById(clubId);
        return new ResponseEntity<>(ClubMapper.toDtoDetail(clubRequest), HttpStatus.OK);
    }

}
