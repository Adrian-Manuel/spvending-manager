package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.UpdateUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutDetail;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/user-managers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PutUserManagerController {
    private final UpdateUserManagerUseCase updateUserManagerUseCase;
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{userManagerId}")
    public ResponseEntity<UserManagerDtoOutDetail> updateUserManager(@PathVariable UUID userManagerId, @Valid @RequestBody UserManagerDtoIn userManagerDtoIn) throws Exception {
        UserManager userManagerRequest= UserManagerMapper.toModel(userManagerDtoIn);
        userManagerRequest= updateUserManagerUseCase.updateUserManager(userManagerDtoIn.getTenantEntityId(),userManagerDtoIn.getClubEntityId(), userManagerId, userManagerRequest);
        return new ResponseEntity<>(UserManagerMapper.toDtoDetail(userManagerRequest), HttpStatus.OK);

    }
}
