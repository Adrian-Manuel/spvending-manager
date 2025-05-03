package com.smart_padel.spvending_management_api.security.auth.service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtService jwtService;
    public void blacklistToken(String token){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        long expiration= jwtService.getJwtExpiration(token);
        redisTemplate.opsForValue().set(token, "blacklisted", expiration, TimeUnit.MILLISECONDS);
    }
    public boolean isBlacklisted(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
