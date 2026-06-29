package com.example.springbootdemo.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void shouldInvalidateTokenAfterLogout() {
        String token = jwtUtil.generateToken("alice");

        assertTrue(jwtUtil.validateToken(token));

        jwtUtil.invalidateToken(token);

        assertFalse(jwtUtil.validateToken(token));
    }
}
