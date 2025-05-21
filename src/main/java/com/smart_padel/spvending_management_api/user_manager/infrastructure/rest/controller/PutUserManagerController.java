package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.UpdateUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoIn;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutDetail;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-managers")
@Tag(name = "User Manager", description = "Update user manager information")
public class PutUserManagerController {
    private final UpdateUserManagerUseCase updateUserManagerUseCase;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;

    @Operation(
            summary = "Update a user manager",
            description = "Updates the information of a user manager by its ID. Requires 'admin:update' authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User manager updated successfully",
                    content = @Content(schema = @Schema(implementation = UserManagerDtoOutDetail.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User manager not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{userManagerId}")
    public ResponseEntity<UserManagerDtoOutDetail> updateUserManager(
            @Parameter(description = "UUID of the user manager to update", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID userManagerId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User manager information for update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserManagerDtoIn.class))
            )
            @Valid @RequestBody UserManagerDtoIn userManagerDtoIn) throws GeneralSecurityException {
        UserManager userManagerRequest= UserManagerMapper.toModel(userManagerDtoIn,aeSecretKey);
        userManagerRequest= updateUserManagerUseCase.updateUserManager(userManagerId, userManagerRequest);
        return new ResponseEntity<>(UserManagerMapper.toDtoDetail(userManagerRequest, aeSecretKey), HttpStatus.OK);
    }
}
