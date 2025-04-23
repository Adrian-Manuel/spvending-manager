package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.UpdateUserManagerUseCase;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoIn;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoOutDetail;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.mapper.UserManagerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-managers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PutUserManagerController {

    private final UpdateUserManagerUseCase updateUserManagerUseCase;

    @PutMapping("/{userManagerId}")
    public ResponseEntity<UserManagerDtoOutDetail> updateUserManager(@PathVariable UUID userManagerId, @Valid @RequestBody UserManagerDtoIn userManagerDtoIn) throws Exception {
        UserManager userManagerRequest= UserManagerMapper.toModel(userManagerDtoIn);
        userManagerRequest= updateUserManagerUseCase.updateUserManager(userManagerDtoIn.getTenantEntityId(),userManagerDtoIn.getClubEntityId(), userManagerId, userManagerRequest);
        return new ResponseEntity<>(UserManagerMapper.toDtoDetail(userManagerRequest), HttpStatus.OK);

    }
}
