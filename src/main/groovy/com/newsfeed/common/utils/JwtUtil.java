package com.newsfeed.common.utils;

import com.newsfeed.common.IgnoreConst;
import com.newsfeed.common.exception.ApplicationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtil {

    private static final SecretKey key = Keys.hmacShaKeyFor(IgnoreConst.JWT_KEY.getBytes(StandardCharsets.UTF_8));

    // JWT 생성
    public static String generateToken(Long userId) {
        Instant now = Instant.now();
        Instant expiration = now.plus(1, ChronoUnit.HOURS);

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(key)
                .compact();
    }

    // JWT 검증
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    // JWT 만료시간 검증 true 반환시 만료된 토큰
    public static boolean validateExpired(String authorization) {
        try {
            String token = authorization.substring(7);
            Long expirationTime = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .getTime();

            return expirationTime < System.currentTimeMillis();
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    // JWT userId 추출
    public static Long extractUserId(String authorization) {
        try {
            String token = authorization.substring(7);

            return Long.parseLong(
                    Jwts.parser()
                            .verifyWith(key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload()
                            .getSubject()
            );
        } catch(ExpiredJwtException e) {
            throw new ApplicationException("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new ApplicationException("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    // 토큰 즉시 만료 시켜서 로그아웃
    public static String invalidateToken(Long userId) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now))
                .signWith(key)
                .compact();
    }


}


