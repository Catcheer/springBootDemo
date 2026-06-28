package com.example.springbootdemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JwtUtil {

    // 密钥（至少 32 位，生产环境请放到配置文件中）
    private static final String SECRET = "springBootDemoJwtSecretKey123456";
    // token 有效期：2 小时
    private static final long EXPIRATION_MS = 2 * 60 * 60 * 1000;

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private static final Set<String> INVALIDATED_TOKENS = ConcurrentHashMap.newKeySet();

    /**
     * 生成 JWT token
     *
     * @param username 用户名，作为 subject 写入 token
     * @return token 字符串
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析 token，返回 Claims（包含 subject、过期时间等信息）
     * 如果 token 非法或已过期，会抛出异常
     *
     * @param token JWT token 字符串
     * @return Claims
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 校验 token 是否合法
     *
     * @param token JWT token 字符串
     * @return true=合法，false=不合法
     */
    public static boolean validateToken(String token) {
        if (token == null || INVALIDATED_TOKENS.contains(token)) {
            return false;
        }

        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将 token 标记为已失效，客户端退出登录后调用
     *
     * @param token JWT token 字符串
     */
    public static void invalidateToken(String token) {
        if (token != null) {
            INVALIDATED_TOKENS.add(token);
        }
    }

    /**
     * 从 token 中获取用户名
     *
     * @param token JWT token 字符串
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }
}
