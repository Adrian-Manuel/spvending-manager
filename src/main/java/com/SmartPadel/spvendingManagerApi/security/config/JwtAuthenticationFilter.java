package com.SmartPadel.spvendingManagerApi.security.config;

import com.SmartPadel.spvendingManagerApi.security.auth.util.CookieUtil;
import com.SmartPadel.spvendingManagerApi.security.auth.util.JwtUtil;
import com.SmartPadel.spvendingManagerApi.security.auth.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    private static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        final String requestURI=request.getRequestURI();
        return requestURI.equals("/api/v1/auth/login");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String  jwtAccess= CookieUtil.getCookieValue(request, ACCESS_TOKEN_COOKIE_NAME);
        if (jwtAccess == null || jwtAccess.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication required: Access token cookie missing or empty.");
                return;
        }
        if (tokenBlacklistService.isBlacklisted(jwtAccess)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("invalid token");
            return;
        }
        final String username = jwtUtil.extractUsername(jwtAccess);
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (userDetails!=null && jwtUtil.isTokenValid(jwtAccess, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
