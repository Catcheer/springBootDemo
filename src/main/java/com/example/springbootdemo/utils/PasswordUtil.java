package com.example.springbootdemo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String md5(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available", e);
        }
    }

    public static boolean matchesPassword(String inputPassword, String storedPassword) {
        if (inputPassword == null || storedPassword == null) {
            return false;
        }

        String normalizedInput = inputPassword.trim();
        String normalizedStored = storedPassword.trim();

        if (normalizedInput.equals(normalizedStored)) {
            return true;
        }

        if (md5(normalizedInput).equalsIgnoreCase(normalizedStored)) {
            return true;
        }

        return md5(normalizedStored).equalsIgnoreCase(normalizedInput);
    }
}
