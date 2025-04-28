package com.SmartPadel.spvendingManagerApi.security.auth.service;

import com.SmartPadel.spvendingManagerApi.security.auth.controller.AuthRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.controller.RegisterRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.controller.TokenResponse;
import com.SmartPadel.spvendingManagerApi.security.auth.util.CookieUtil;
import com.SmartPadel.spvendingManagerApi.security.auth.util.JwtUtil;
import com.SmartPadel.spvendingManagerApi.security.user.JpaUserRepository;
import com.SmartPadel.spvendingManagerApi.security.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final String REFRESH_TOKEN_COOKIE_NAME="refresh_token";
    private final String ACCESS_TOKEN_COOKIE_NAME="access_token";
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


    public TokenResponse authenticate(final AuthRequest request, final HttpServletResponse response){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        final User user= jpaUserRepository.findByUsername(request.username()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        final String accessToken = jwtUtil.generateToken(user);
        final String refreshToken = jwtUtil.generateRefreshToken(user);

        Cookie accessTokenCookie=CookieUtil.createCookie(ACCESS_TOKEN_COOKIE_NAME,accessToken,60 * 15,true,true,"/");
        Cookie refreshTokenCookie=CookieUtil.createCookie(REFRESH_TOKEN_COOKIE_NAME,refreshToken,60*60*24,true,true, "/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response) throws IOException {
        String refreshTokenValue = CookieUtil.getCookieValue(request,REFRESH_TOKEN_COOKIE_NAME);
        if (refreshTokenValue == null || refreshTokenValue.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication required: Refresh token cookie missing or empty.");
        }
        final String username = jwtUtil.extractUsername(refreshTokenValue);
        if (username==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Refresh Token: Username cant be found");
        }
        final User user = this.jpaUserRepository.findByUsername(username).orElseThrow();
        final boolean isTokenValid= jwtUtil.isTokenValid(refreshTokenValue, user);
        if (!isTokenValid) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid refresh token: Token validation failed.");
        }
        final  String accessToken= jwtUtil.generateRefreshToken(user);
        Cookie newAccessTokenCookie=CookieUtil.createCookie(ACCESS_TOKEN_COOKIE_NAME,accessToken,60 * 15,true,true,"/");
        response.addCookie(newAccessTokenCookie);
        return new TokenResponse(accessToken, refreshTokenValue);
    }

}
