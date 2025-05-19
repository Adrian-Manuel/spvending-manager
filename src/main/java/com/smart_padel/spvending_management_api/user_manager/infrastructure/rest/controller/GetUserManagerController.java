package com.smart_padel.spvending_management_api.user_manager.infrastructure.rest.controller;
import com.smart_padel.spvending_management_api.user_manager.domain.model.UserManager;
import com.smart_padel.spvending_management_api.user_manager.domain.ports.in.RetrieveUserManagerUseCase;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutDetail;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerDtoOutPreview;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.UserManagerPreviewSwagger;
import com.smart_padel.spvending_management_api.user_manager.infrastructure.dto.mapper.UserManagerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/user-managers")
@RequiredArgsConstructor
@Tag(name = "User Manager", description = "Retrieve user manager information")
public class GetUserManagerController {
    private final RetrieveUserManagerUseCase retrieveUserManagerUseCase;
    @Value("${app.AESecret_key}")
    private String aeSecretKey;

    @Operation(
            summary = "Get all user managers with pagination and optional search",
            description = "Returns a paginated list of user managers. Accessible to admin and user with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paginated list of user managers",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UserManagerPreviewSwagger.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping
    public ResponseEntity<Page<UserManagerDtoOutPreview>> getAllUserManagers(
            @Parameter(description = "Search string to filter user managers by username or other criteria", example = "padel")
            @RequestParam(required = false) String search,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<UserManagerDtoOutPreview> userManagers= retrieveUserManagerUseCase.getAllUserManager(search, pageable).map(UserManagerMapper::toDtoPreview);
        return new ResponseEntity<>(userManagers, HttpStatus.OK);
    }

    @Operation(
            summary = "Get user manager by ID",
            description = "Returns detailed information of a user manager by its UUID. Accessible to admin and user with read authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User manager found",
                    content = @Content(schema = @Schema(implementation = UserManagerDtoOutDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "User manager not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    @GetMapping("/{userManagerId}")
    public ResponseEntity<UserManagerDtoOutDetail> getUserManagerById(
            @Parameter(description = "UUID of the user manager", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
            @PathVariable UUID userManagerId) throws GeneralSecurityException {
        UserManager userManagerRequest=retrieveUserManagerUseCase.getUserManagerById(userManagerId);
        return new ResponseEntity<>(UserManagerMapper.toDtoDetail(userManagerRequest, aeSecretKey),HttpStatus.OK);
    }

}
