package com.smart_padel.spvending_management_api.security.auth.service;

import com.smart_padel.spvending_management_api.security.user.Role;
import com.smart_padel.spvending_management_api.security.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private JwtService jwtService;
    private User user;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "ZmFrZXNlY3JldGtleWZha2VzZWNyZXRrZXkzMmZha2V5");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 600_000L); // 10 minutes
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 3_600_000L); // 1 hour
        user = User.builder().username("testUser").password("password").role(Role.USER).build();
    }

    @Test
    void extractUsername_returnsCorrectUsername() {
        String token = jwtService.generateToken(user);
        assertThat(jwtService.extractUsername(token)).isEqualTo(user.getUsername());
    }

    @Test
    void extractUsername_throwsForMalformedToken() {
        assertThrows(Exception.class, () -> jwtService.extractUsername("malformed.token.value"));
    }

    @Test
    void generateToken_createsValidToken() {
        String token = jwtService.generateToken(user);
        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo(user.getUsername());
        assertThat(jwtService.getJwtExpiration(token)).isGreaterThan(0);
    }

    @Test
    void generateRefreshToken_createsTokenWithLongerExpiration() {
        String refreshToken = jwtService.generateRefreshToken(user);
        assertThat(jwtService.getJwtExpiration(refreshToken))
            .isGreaterThan(jwtService.getJwtExpiration(jwtService.generateToken(user)));
    }

    @Test
    void isTokenValid_checksTokenValidity() {
        String token = jwtService.generateToken(user);
        assertThat(jwtService.isTokenValid(token, user)).isTrue();
        assertThat(jwtService.isTokenValid(token, User.builder().username("otherUser").build())).isFalse();
        assertThat(jwtService.isTokenValid(null, user)).isFalse();
    }

    @Test
    void isTokenExpired_checksExpiration() {
        String token = jwtService.generateToken(user);
        assertThat(jwtService.isTokenExpired(token)).isFalse();
    }

    @Test
    void getJwtExpiration_returnsRemainingTime() {
        String token = jwtService.generateToken(user);
        assertThat(jwtService.getJwtExpiration(token)).isGreaterThan(0);
    }

    @Test
    void extractExpiration_returnsExpirationDate() {
        String token = jwtService.generateToken(user);
        assertNotNull(jwtService.extractExpiration(token));
        assertTrue(jwtService.extractExpiration(token).after(new Date()));
    }

    @Test
    void methods_throwForNullToken() {
        assertThrows(IllegalArgumentException.class, () -> jwtService.extractUsername(null));
        assertThrows(IllegalArgumentException.class, () -> jwtService.extractExpiration(null));
        assertThrows(IllegalArgumentException.class, () -> jwtService.isTokenExpired(null));
        assertThrows(IllegalArgumentException.class, () -> jwtService.getJwtExpiration(null));
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenTokenThrowsJwtException() {
        String invalidToken = "invalid.token.value";
        UserDetails mockUser = Mockito.mock(UserDetails.class);
        Mockito.when(mockUser.getUsername()).thenReturn("usuario");
        boolean result = jwtService.isTokenValid(invalidToken, mockUser);
        assertFalse(result);
    }
}