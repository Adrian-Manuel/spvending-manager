package com.smart_padel.spvending_management_api.security.config;
import com.smart_padel.spvending_management_api.security.auth.service.TokenBlacklistService;
import com.smart_padel.spvending_management_api.security.auth.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenBlacklistService tokenBlacklistService;

    public void logout(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
        String accessToken= CookieUtil.getCookieValue(request, "access_token");
        String refreshToken=CookieUtil.getCookieValue(request, "refresh_token");

        if (refreshToken!=null && refreshToken.startsWith("Bearer ")){
            refreshToken = refreshToken.substring(7);
            tokenBlacklistService.blacklistToken(refreshToken);
        }
        if (accessToken!=null && accessToken.startsWith("Bearer ")){
             accessToken = accessToken.substring(7);
            tokenBlacklistService.blacklistToken(accessToken);
        }
        SecurityContextHolder.clearContext();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->

                        req.requestMatchers("/api/v1/auth/**", "/swagger-ui.html", "/swagger-ui/**","/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                                .anyRequest().authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(this::logout)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                                .deleteCookies("access_token", "refresh_token")
                )
        ;
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:80", "http://localhost:3000", "http://localhost"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
