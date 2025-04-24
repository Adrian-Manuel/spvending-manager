package com.SmartPadel.spvendingManagerApi.security.auth.service;

import com.SmartPadel.spvendingManagerApi.security.auth.controller.AuthRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.controller.RegisterRequest;
import com.SmartPadel.spvendingManagerApi.security.auth.controller.TokenResponse;
import com.SmartPadel.spvendingManagerApi.security.auth.repository.Token;
import com.SmartPadel.spvendingManagerApi.security.auth.repository.TokenRepository;
import com.SmartPadel.spvendingManagerApi.security.user.JpaUserRepository;
import com.SmartPadel.spvendingManagerApi.security.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JpaUserRepository jpaUserRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private void saveUserToken(User user, String jwtToken){
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponse register(final RegisterRequest request){
        final User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        final User savedUser = jpaUserRepository.save(user);
        final String jwtToken = jwtService.gerateToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private void revokeAllUserTokens(final User user){
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (!validUserTokens.isEmpty()){
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse authenticate(final AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        final User user= jpaUserRepository.findByUsername(request.username()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        final String accesToken = jwtService.gerateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accesToken);
        return new TokenResponse(accesToken, refreshToken);
    }

    public TokenResponse refreshToken(@NotNull final String authentication){
        if (authentication == null || !authentication.startsWith("Bearer ")){
            throw new IllegalArgumentException("invalid auth header");
        }

        final String refreshToken = authentication.substring(7);

        final String username = jwtService.extractUsername(refreshToken);

        if (username==null){
            return null;
        }

        final User user = this.jpaUserRepository.findByUsername(username).orElseThrow();
        final boolean isTokenValid= jwtService.isTokenValid(refreshToken, user);

        if (!isTokenValid){
            return null;
        }

        final  String accesToken= jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accesToken);

        return new TokenResponse(accesToken, refreshToken);
    }

}
