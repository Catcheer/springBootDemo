package com.example.springbootdemo.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    @Test
    void shouldInvalidateTokenAfterLogout() {
        String token = JwtUtil.generateToken("alice");

        assertTrue(JwtUtil.validateToken(token));

        JwtUtil.invalidateToken(token);

        assertFalse(JwtUtil.validateToken(token));
    }
}
