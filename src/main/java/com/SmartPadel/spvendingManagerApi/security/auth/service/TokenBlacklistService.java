package com.SmartPadel.spvendingManagerApi.security.auth.service;

import com.SmartPadel.spvendingManagerApi.security.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    public void blacklistToken(String token){
        long expiration= jwtUtil.getJwtExpiration(token);
        redisTemplate.opsForValue().set(token, "blacklisted", expiration, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
