package com.smart_padel.spvending_management_api.security.config;
import com.smart_padel.spvending_management_api.security.auth.util.CookieUtil;
import com.smart_padel.spvending_management_api.security.auth.service.JwtService;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@RequiredArgsConstructor
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    private static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        final String requestURI=request.getRequestURI();
        return requestURI.equals("/api/v1/auth/login")
                || requestURI.equals("/api/v1/auth/register")
                || requestURI.equals("/swagger-ui.html")
                || requestURI.startsWith("/swagger-ui/")
                || requestURI.equals("/v3/api-docs")
                || requestURI.startsWith("/v3/api-docs/")
                || requestURI.equals("/v3/api-docs.yaml");
    }
    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
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
        final String username = jwtService.extractUsername(jwtAccess);
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (userDetails!=null && jwtService.isTokenValid(jwtAccess, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
