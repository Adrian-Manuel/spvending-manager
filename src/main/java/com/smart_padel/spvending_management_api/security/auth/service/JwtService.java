package com.smart_padel.spvending_management_api.security.auth.service;
import com.smart_padel.spvending_management_api.security.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    private SecretKey getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUsername(String token){
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    private String buildToken(final User user, final Long expiration){
        List<String> roles=user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts
                .builder()
                .claims(Map.of("name", user.getUsername(), "role", roles))
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSignInKey())
                .compact();
    }
    public String generateToken(final User user){
        return buildToken(user, jwtExpiration);
    }
    public String generateRefreshToken(final User user){
        return buildToken(user, refreshExpiration);
    }
    Date extractExpiration(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
    boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return true;
        }
    }
    public Boolean isTokenValid(String token, UserDetails user){
        try {
            if (token == null || token.trim().isEmpty()) {
                return false;
            }
            final String username = extractUsername(token);
            return (username.equals(user.getUsername())) && !isTokenExpired(token);
        } catch (Exception exception) {
            return false;
        }
    }
    public long getJwtExpiration(String token){
        Claims claims=Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Date expiration=claims.getExpiration();
        return expiration.getTime() - System.currentTimeMillis();
    }
}
