package com.SmartPadel.spvendingManagerApi.security.auth.controller;
import com.SmartPadel.spvendingManagerApi.security.auth.dto.AuthRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.dto.RegisterRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.dto.TokenResponse;
import com.SmartPadel.spvendingManagerApi.security.auth.dto.UserResponse;
import com.SmartPadel.spvendingManagerApi.security.auth.service.AuthService;
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
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        final TokenResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@RequestBody AuthRequest request, HttpServletResponse httpServletResponse) {
        final UserResponse response = service.authenticate(request, httpServletResponse);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@NotNull HttpServletResponse httpServletResponse, @NotNull HttpServletRequest httpServletRequest) throws IOException {
        return service.refreshToken(httpServletRequest, httpServletResponse);
    }
}
