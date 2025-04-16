package com.SmartPadel.spvendingManagerApi.club.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.club.domain.ports.in.DeleteClubUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
public class DeleteClubController {
    private final DeleteClubUseCase deleteClubUseCase;

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClubById(@PathVariable UUID clubId) {
        deleteClubUseCase.deleteClub(clubId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}