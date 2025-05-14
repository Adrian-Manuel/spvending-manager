package com.smart_padel.spvending_management_api.security.auth.config;

import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.security.auth.util.CookieUtil;
import com.smart_padel.spvending_management_api.security.config.JwtAuthenticationFilter;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldNotFilterWhenRequestUriStartsWithAuth() throws ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/auth/login");

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenBlacklistService);
        assertThat(filter.shouldNotFilter(request)).isTrue();
    }

    @Test
    void shouldFilterWhenRequestUriDoesNotStartWithAuth() throws ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/resource");

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenBlacklistService);
        assertThat(filter.shouldNotFilter(request)).isFalse();
    }

    @Test
    void doFilterInternalReturnsUnauthorizedWhenAccessTokenIsMissing() throws ServletException, java.io.IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(CookieUtil.getCookieValue(request, "access_token")).thenReturn(null);
        when(response.getWriter()).thenReturn(mock(java.io.PrintWriter.class));

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenBlacklistService);
        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Authentication required: Access token cookie missing or empty.");
        verifyNoInteractions(filterChain);
    }

    @Test
    void doFilterInternalReturnsUnauthorizedWhenTokenIsBlacklisted() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        Cookie blacklistedCookie = new Cookie("access_token", "blacklistedToken");
        when(request.getCookies()).thenReturn(new Cookie[]{blacklistedCookie});
        when(tokenBlacklistService.isBlacklisted("blacklistedToken")).thenReturn(true);
        when(response.getWriter()).thenReturn(mock(java.io.PrintWriter.class));

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenBlacklistService);
        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("invalid token");
        verifyNoInteractions(filterChain);
    }

    @Test
    void doFilterInternalAuthenticatesWhenTokenIsValid() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);
        Cookie validCookie = new Cookie("access_token", "validToken");
        when(request.getCookies()).thenReturn(new Cookie[]{validCookie});
        when(jwtService.extractUsername("validToken")).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtService.isTokenValid("validToken", userDetails)).thenReturn(true);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenBlacklistService);
        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        verify(filterChain).doFilter(request, response);
    }
}
