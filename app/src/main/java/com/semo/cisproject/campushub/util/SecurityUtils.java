package com.semo.cisproject.campushub.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static String sha256(String input) {
        if (input == null) {
            return "";
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

    public static boolean isEduEmail(String email) {
        if (email == null) {
            return false;
        }
        String normalized = email.trim().toLowerCase();
        return normalized.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.edu$");
    }

    public static boolean isStrongEnoughPassword(String password) {
        return password != null && password.trim().length() >= 6;
    }

    public static boolean safeEquals(String left, String right) {
        if (left == null || right == null) {
            return false;
        }
        return left.equals(right);
    }
}
