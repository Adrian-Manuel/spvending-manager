package com.smart_padel.spvending_management_api.security.auth.controller;
import com.smart_padel.spvending_management_api.security.auth.dto.AuthRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.RegisterRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.UserResponse;
import com.smart_padel.spvending_management_api.security.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration, authentication, and token refresh")
public class AuthController {
    private final AuthService service;

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user and returns their username and role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid registration data", content = @Content),
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Registration information",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class))
            )
            @RequestBody @Valid RegisterRequest request) {
        final UserResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Authenticate user (login)",
            description = "Authenticates the user and returns their username and role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid authentication data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication information",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AuthRequest.class))
            )
            @RequestBody @Valid AuthRequest request, HttpServletResponse httpServletResponse) {
        final UserResponse response = service.authenticate(request, httpServletResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Refresh token",
            description = "Refreshes the authentication token using the current request and response."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token refreshed successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/refresh-token")
    public UserResponse refreshToken(
            @NotNull HttpServletResponse httpServletResponse,
            @NotNull HttpServletRequest httpServletRequest) throws IOException {
        return service.refreshToken(httpServletRequest, httpServletResponse);
    }
}
