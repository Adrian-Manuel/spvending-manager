package com.SmartPadel.spvendingManagerApi.userManager.infrastructure.rest.controller;

import com.SmartPadel.spvendingManagerApi.club.infrastructure.dto.mapper.ClubMapper;
import com.SmartPadel.spvendingManagerApi.userManager.domain.model.UserManager;
import com.SmartPadel.spvendingManagerApi.userManager.domain.ports.in.CreateUserManagerUseCase;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoIn;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.UserManagerDtoOutPreview;
import com.SmartPadel.spvendingManagerApi.userManager.infrastructure.dto.mapper.UserManagerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-managers")
public class PostUserManagerController {
    private final CreateUserManagerUseCase createUserManagerUseCase;

    @PostMapping
    public ResponseEntity<UserManagerDtoOutPreview> createUserManager(@RequestBody @Valid UserManagerDtoIn userManagerDtoIn) throws Exception {
        UserManager userManagerRequest= UserManagerMapper.toModel(userManagerDtoIn);
        userManagerRequest=createUserManagerUseCase.createUserManager(userManagerDtoIn.getTenantEntityId(), userManagerDtoIn.getClubEntityId(), userManagerRequest);
        return new ResponseEntity<>(UserManagerMapper.toDtoPreview(userManagerRequest), HttpStatus.CREATED);
    }
}
