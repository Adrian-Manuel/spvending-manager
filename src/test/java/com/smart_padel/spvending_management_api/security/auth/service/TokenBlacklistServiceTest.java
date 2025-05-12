package com.smart_padel.spvending_management_api.security.auth.service;

    import com.smart_padel.spvending_management_api.security.user.Role;
    import com.smart_padel.spvending_management_api.security.user.User;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.data.redis.core.ValueOperations;
    import org.springframework.test.util.ReflectionTestUtils;

    import java.util.concurrent.TimeUnit;

    import static org.assertj.core.api.Assertions.assertThat;
    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    class TokenBlacklistServiceTest {

        @Mock
        private RedisTemplate<String, String> redisTemplate;

        @Mock
        private JwtService jwtService;

        @Mock
        private ValueOperations<String, String> valueOperations;

        @InjectMocks
        private TokenBlacklistService tokenBlacklistService;

        private final JwtService realJwtService = new JwtService();

        private User createTestUser() {
            return User.builder()
                    .id(1)
                    .username("testuser")
                    .password("password")
                    .role(Role.USER)
                    .build();
        }

        @Test
        void blacklistToken_storesTokenInRedis() {
            String token = "Bearer validToken";
            String strippedToken = "validToken";
            long expiration = 60000L;

            when(jwtService.getJwtExpiration(strippedToken)).thenReturn(expiration);
            when(redisTemplate.opsForValue()).thenReturn(valueOperations);

            tokenBlacklistService.blacklistToken(token);

            verify(valueOperations).set(strippedToken, "blacklisted", expiration, TimeUnit.MILLISECONDS);
        }

        @Test
        void blacklistToken_handlesTokenWithoutPrefix() {
            String token = "validToken";
            long expiration = 60000L;

            when(jwtService.getJwtExpiration(token)).thenReturn(expiration);
            when(redisTemplate.opsForValue()).thenReturn(valueOperations);

            tokenBlacklistService.blacklistToken(token);

            verify(valueOperations).set(token, "blacklisted", expiration, TimeUnit.MILLISECONDS);
        }

        @Test
        void isBlacklisted_checksIfTokenExistsInRedis() {
            String token = "validToken";

            when(redisTemplate.hasKey(token)).thenReturn(true);
            assertTrue(tokenBlacklistService.isBlacklisted(token));

            when(redisTemplate.hasKey(token)).thenReturn(false);
            assertFalse(tokenBlacklistService.isBlacklisted(token));
        }

        @Test
        void extractExpiration_throwsForMalformedToken() {
            ReflectionTestUtils.setField(realJwtService, "secretKey", "ZmFrZXNlY3JldGtleWZha2VzZWNyZXRrZXlGb3JIVTI1Ng==");
            String malformedToken = "malformed.token.value";

            assertThrows(io.jsonwebtoken.JwtException.class, () -> realJwtService.extractExpiration(malformedToken));
        }

        @Test
        void isTokenExpired_checksForExpiredToken() {
            ReflectionTestUtils.setField(realJwtService, "secretKey", "ZmFrZXNlY3JldGtleWZha2VzZWNyZXRrZXlGb3JIVTI1Ng==");
            ReflectionTestUtils.setField(realJwtService, "jwtExpiration", -1000L);
            User user = createTestUser();
            String expiredToken = realJwtService.generateToken(user);

            assertTrue(realJwtService.isTokenExpired(expiredToken));
        }

        @Test
        void getJwtExpiration_returnsNegativeForExpiredToken() {
            ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1000L);
            User user = createTestUser();
            String expiredToken = jwtService.generateToken(user);

            assertThat(jwtService.getJwtExpiration(expiredToken)).isLessThanOrEqualTo(0);
        }
    }