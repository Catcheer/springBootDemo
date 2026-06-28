package com.example.springbootdemo.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PasswordUtilTest {

    @Test
    void shouldMatchPlaintextPasswordAgainstPlaintextStoredPassword() {
        assertTrue(PasswordUtil.matchesPassword("123456", "123456"));
    }

    @Test
    void shouldMatchMd5PasswordAgainstPlaintextStoredPassword() {
        assertTrue(PasswordUtil.matchesPassword("e10adc3949ba59abbe56e057f20f883e", "123456"));
    }

    @Test
    void shouldRejectWrongPassword() {
        assertFalse(PasswordUtil.matchesPassword("654321", "123456"));
    }
}
