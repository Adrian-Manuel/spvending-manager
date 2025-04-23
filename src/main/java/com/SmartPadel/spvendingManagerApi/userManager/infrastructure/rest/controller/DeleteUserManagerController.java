package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.DeleteUserManagerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/user-managers")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeleteUserManagerController {
    private final DeleteUserManagerUseCase deleteUserManagerUseCase;

    @DeleteMapping("/{userManagerId}")
    public ResponseEntity<Void> deleteUserManager (@PathVariable UUID userManagerId){
        deleteUserManagerUseCase.deleteUserManager(userManagerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
