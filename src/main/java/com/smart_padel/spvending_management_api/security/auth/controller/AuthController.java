package com.smart_padel.spvending_management_api.security.auth.controller;
import com.smart_padel.spvending_management_api.security.auth.dto.AuthRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.RegisterRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.UserResponse;
import com.smart_padel.spvending_management_api.security.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        final UserResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@RequestBody AuthRequest request, HttpServletResponse httpServletResponse) {
        final UserResponse response = service.authenticate(request, httpServletResponse);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh-token")
    public UserResponse refreshToken(@NotNull HttpServletResponse httpServletResponse, @NotNull HttpServletRequest httpServletRequest) throws IOException {
        return service.refreshToken(httpServletRequest, httpServletResponse);
    }
}
