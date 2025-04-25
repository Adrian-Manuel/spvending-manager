package com.SmartPadel.spvendingManagerApi.security.auth.service;

import com.SmartPadel.spvendingManagerApi.security.auth.controller.AuthRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.controller.RegisterRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.controller.TokenResponse;
import com.SmartPadel.spvendingManagerApi.security.auth.util.JwtUtil;
import com.SmartPadel.spvendingManagerApi.security.user.JpaUserRepository;
import com.SmartPadel.spvendingManagerApi.security.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;



    public TokenResponse register(final RegisterRequest request){
        final User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        final User savedUser = jpaUserRepository.save(user);
        final String jwtToken = jwtUtil.generateToken(savedUser);
        final String refreshToken = jwtUtil.generateRefreshToken(savedUser);
        return new TokenResponse(jwtToken, refreshToken);
    }


    public TokenResponse authenticate(final AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        final User user= jpaUserRepository.findByUsername(request.username()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        final String accessToken = jwtUtil.generateToken(user);
        final String refreshToken = jwtUtil.generateRefreshToken(user);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(@NotNull final String authentication){
        if (authentication == null || !authentication.startsWith("Bearer ")){
            throw new IllegalArgumentException("invalid auth header");
        }

        final String refreshToken = authentication.substring(7);
        final String username = jwtUtil.extractUsername(refreshToken);

        if (username==null){
            return null;
        }

        final User user = this.jpaUserRepository.findByUsername(username).orElseThrow();
        final boolean isTokenValid= jwtUtil.isTokenValid(refreshToken, user);

        if (!isTokenValid){
            return null;
        }

        final  String accessToken= jwtUtil.generateRefreshToken(user);

        return new TokenResponse(accessToken, refreshToken);
    }

}
