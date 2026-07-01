package com.example.springbootdemo.utils;

import com.example.springbootdemo.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final SecretKey key;
    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return generateAccessToken(username);
    }

    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        if (token == null || invalidatedTokens.contains(token)) {
            return false;
        }

        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void invalidateToken(String token) {
        if (token != null) {
            invalidatedTokens.add(token);
        }
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public String parseUserId(String token) {
        if (token == null) return null;
        try {
            return parseToken(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    
}
