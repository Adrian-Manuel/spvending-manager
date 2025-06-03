package com.smart_padel.spvending_management_api.security.auth.service;
import com.smart_padel.spvending_management_api.security.auth.dto.AuthRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.RegisterRequest;
import com.smart_padel.spvending_management_api.security.auth.dto.UserResponse;
import com.smart_padel.spvending_management_api.security.auth.util.CookieUtil;
import com.smart_padel.spvending_management_api.security.auth.repository.JpaUserRepository;
import com.smart_padel.spvending_management_api.security.user.User;
import com.smart_padel.spvending_management_api.shared.exceptions.ResourceAlreadyExistsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final String REFRESH_TOKEN_COOKIE_NAME="refresh_token";
    private static final String ACCESS_TOKEN_COOKIE_NAME="access_token";
    @Transactional
    public UserResponse register(final RegisterRequest request){
        if (jpaUserRepository.findByUsername(request.getUsername()).isPresent()){
            throw new ResourceAlreadyExistsException("User already exists");
        }

        final User user = User.builder()
                .username(request.getUsername().trim())
                .password(passwordEncoder.encode(request.getPassword().trim()))
                .role(request.getRole())
                .build();
        final User savedUser = jpaUserRepository.save(user);
        return new UserResponse(savedUser.getUsername(), savedUser.getRole());
    }
    public UserResponse authenticate(final AuthRequest request, final HttpServletResponse response){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        final User user= jpaUserRepository.findByUsername(request.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        Cookie accessTokenCookie=CookieUtil.createCookie(ACCESS_TOKEN_COOKIE_NAME,accessToken,60 * 15,true,false,"/", "Lax");
        Cookie refreshTokenCookie=CookieUtil.createCookie(REFRESH_TOKEN_COOKIE_NAME,refreshToken,60*60*24,true,false, "/", "Lax");
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return new UserResponse(user.getUsername(), user.getRole());
    }
    public UserResponse refreshToken(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response) throws IOException {
        String refreshTokenValue = CookieUtil.getCookieValue(request,REFRESH_TOKEN_COOKIE_NAME);
        if (refreshTokenValue == null || refreshTokenValue.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication required: Refresh token cookie missing or empty.");
            return null;
        }
        final String username = jwtService.extractUsername(refreshTokenValue);
        if (username==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Refresh Token: Username cant be found");
            return null;
        }
        final User user = this.jpaUserRepository.findByUsername(username).orElseThrow();
        final boolean isTokenValid= jwtService.isTokenValid(refreshTokenValue, user);
        if (!isTokenValid) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid refresh token: Token validation failed.");
            return null;
        }
        final  String accessToken= jwtService.generateToken(user);
        Cookie newAccessTokenCookie=CookieUtil.createCookie(ACCESS_TOKEN_COOKIE_NAME,accessToken,60 * 15,true,false,"/", "Lax");
        response.addCookie(newAccessTokenCookie);
        return new UserResponse(user.getUsername(), user.getRole());
    }

}
