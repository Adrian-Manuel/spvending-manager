package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.CreateUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutPreview;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.GeneralSecurityException;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-managers")
@Tag(name = "User Manager", description = "Create user manager")
public class PostUserManagerController {
    private final CreateUserManagerUseCase createUserManagerUseCase;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;

    @Operation(
            summary = "Create a new user manager",
            description = "Creates a new user manager and returns its preview information. Requires 'admin:create' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User manager created successfully",
                    content = @Content(schema = @Schema(implementation = UserManagerDtoOutPreview.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<UserManagerDtoOutPreview> createUserManager(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User manager information for creation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserManagerDtoIn.class))
            )
            @RequestBody @Valid UserManagerDtoIn userManagerDtoIn) throws GeneralSecurityException {
        UserManager userManagerRequest= UserManagerMapper.toModel(userManagerDtoIn, aeSecretKey);
        userManagerRequest=createUserManagerUseCase.createUserManager(userManagerDtoIn.getTenantEntityId(), userManagerDtoIn.getClubEntityId(), userManagerRequest);
        return new ResponseEntity<>(UserManagerMapper.toDtoPreview(userManagerRequest), HttpStatus.CREATED);
    }
}
