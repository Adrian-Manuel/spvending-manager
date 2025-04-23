package com.SmartPadel.spvendingManagerApi.club.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.DeleteClubUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeleteClubController {
    private final DeleteClubUseCase deleteClubUseCase;

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClubById(@PathVariable UUID clubId) {
        deleteClubUseCase.deleteClub(clubId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}