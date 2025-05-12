package com.smart_padel.spvending_management_api.security.auth.service;
import org.springframework.security.authentication.AuthenticationManager;
import com.smart_padel.spvending_management_api.security.auth.dto.AuthRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.RegisterRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.UserResponse;
import com.smart_padel.spvending_management_api.security.auth.repository.JpaUserRepository;
import com.smart_padel.spvending_management_api.security.user.Role;
import com.smart_padel.spvending_management_api.security.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private JpaUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authManager;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userRepository, passwordEncoder, jwtService, authManager);
    }

    @Test
    void register_createsUserResponse() {
        RegisterRequest request = new RegisterRequest("testUser", "testPassword", Role.USER);
        User user = User.builder().username("testUser").password("encodedPassword").role(Role.USER).build();
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserResponse response = authService.register(request);
        assertThat(response.name()).isEqualTo("testUser");
        assertThat(response.role()).isEqualTo(Role.USER);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void authenticate_setsCookies() {
        AuthRequest request = new AuthRequest("testUser", "testPassword");
        User user = User.builder().username("testUser").role(Role.USER).build();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");
        HttpServletResponse response = mock(HttpServletResponse.class);
        UserResponse userResponse = authService.authenticate(request, response);
        assertThat(userResponse.name()).isEqualTo("testUser");
        assertThat(userResponse.role()).isEqualTo(Role.USER);
        verify(response, times(2)).addCookie(any(Cookie.class));
    }

    @Test
    void refreshToken_setsNewAccessTokenCookie() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = User.builder().username("testUser").role(Role.USER).build();
        Cookie refreshCookie = new Cookie("refresh_token", "validRefreshToken");
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});
        when(jwtService.extractUsername("validRefreshToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("validRefreshToken", user)).thenReturn(true);
        when(jwtService.generateRefreshToken(user)).thenReturn("newAccessToken");
        UserResponse userResponse = authService.refreshToken(request, response);
        assertThat(userResponse.name()).isEqualTo("testUser");
        assertThat(userResponse.role()).isEqualTo(Role.USER);
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void refreshToken_missingToken_unauthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getCookies()).thenReturn(null);
        when(response.getWriter()).thenReturn(mock(java.io.PrintWriter.class));
        authService.refreshToken(request, response);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Authentication required: Refresh token cookie missing or empty.");
    }

    @Test
    void refreshToken_invalidToken_unauthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = User.builder().username("testUser").role(Role.USER).build();
        Cookie refreshCookie = new Cookie("refresh_token", "invalidRefreshToken");
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});
        when(jwtService.extractUsername("invalidRefreshToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("invalidRefreshToken", user)).thenReturn(false);
        when(response.getWriter()).thenReturn(mock(java.io.PrintWriter.class));
        authService.refreshToken(request, response);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Invalid refresh token: Token validation failed.");
    }

    @Test
    void refreshToken_nullUsername_unauthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie refreshCookie = new Cookie("refresh_token", "someToken");
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});
        when(jwtService.extractUsername("someToken")).thenReturn(null);
        when(response.getWriter()).thenReturn(mock(java.io.PrintWriter.class));
        authService.refreshToken(request, response);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Invalid Refresh Token: Username cant be found");
    }

    @Test
    void refreshToken_userNotFound_exception() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie refreshCookie = new Cookie("refresh_token", "validRefreshToken");
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});
        when(jwtService.extractUsername("validRefreshToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> authService.refreshToken(request, response));
    }

    @Test
    void authenticate_userNotFound_exception() {
        AuthRequest request = new AuthRequest("notFoundUser", "anyPassword");
        when(userRepository.findByUsername("notFoundUser")).thenReturn(Optional.empty());
        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> authService.authenticate(request, mock(HttpServletResponse.class)));
    }

    @Test
    void refreshToken_returnsUnauthorizedForInvalidToken() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie refreshCookie = new Cookie("refresh_token", "invalidRefreshToken");
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});
        when(jwtService.extractUsername("invalidRefreshToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(User.builder().username("testUser").role(Role.USER).build()));
        when(jwtService.isTokenValid(eq("invalidRefreshToken"), any(User.class))).thenReturn(false);
        when(response.getWriter()).thenReturn(mock(java.io.PrintWriter.class));

        authService.refreshToken(request, response);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Invalid refresh token: Token validation failed.");
    }

    @Test
    void refreshToken_createsNewAccessTokenForValidRefreshToken() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = User.builder().username("testUser").role(Role.USER).build();
        Cookie refreshCookie = new Cookie("refresh_token", "validRefreshToken");
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});
        when(jwtService.extractUsername("validRefreshToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("validRefreshToken", user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("newAccessToken");

        UserResponse userResponse = authService.refreshToken(request, response);

        assertThat(userResponse.name()).isEqualTo("testUser");
        assertThat(userResponse.role()).isEqualTo(Role.USER);
        verify(response).addCookie(any(Cookie.class));
    }
}