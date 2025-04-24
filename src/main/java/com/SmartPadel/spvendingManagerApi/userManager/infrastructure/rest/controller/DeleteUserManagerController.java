package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.DeleteUserManagerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/user-managers")
@RequiredArgsConstructor
@RestController
public class DeleteUserManagerController {
    private final DeleteUserManagerUseCase deleteUserManagerUseCase;
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{userManagerId}")
    public ResponseEntity<Void> deleteUserManager (@PathVariable UUID userManagerId){
        deleteUserManagerUseCase.deleteUserManager(userManagerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
