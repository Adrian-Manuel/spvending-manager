package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.CreateUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutPreview;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.GeneralSecurityException;
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/user-managers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostUserManagerController {
    private final CreateUserManagerUseCase createUserManagerUseCase;
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<UserManagerDtoOutPreview> createUserManager(@RequestBody @Valid UserManagerDtoIn userManagerDtoIn) throws GeneralSecurityException {
        UserManager userManagerRequest= UserManagerMapper.toModel(userManagerDtoIn);
        userManagerRequest=createUserManagerUseCase.createUserManager(userManagerDtoIn.getTenantEntityId(), userManagerDtoIn.getClubEntityId(), userManagerRequest);
        return new ResponseEntity<>(UserManagerMapper.toDtoPreview(userManagerRequest), HttpStatus.CREATED);
    }
}
