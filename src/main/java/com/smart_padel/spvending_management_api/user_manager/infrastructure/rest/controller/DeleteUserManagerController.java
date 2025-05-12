package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.DeleteUserManagerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RequestMapping("/api/v1/user-managers")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeleteUserManagerController {
    private final DeleteUserManagerUseCase deleteUserManagerUseCase;
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{userManagerId}")
    public ResponseEntity<Void> deleteUserManager (@PathVariable UUID userManagerId){
        deleteUserManagerUseCase.deleteUserManager(userManagerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
