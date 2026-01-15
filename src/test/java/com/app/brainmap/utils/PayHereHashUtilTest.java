package com.app.brainmap.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PayHereHashUtilTest {

    // Generate a payment hash (MD5)
    public static String generatePaymentHash(String merchantId, String orderId, BigDecimal amount, String currency, String merchantSecret) {
        try {
            String hashString = merchantId + orderId + amount.toPlainString() + currency + merchantSecret;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(hashString.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    // Generate a unique payment ID
    public static String generatePaymentId() {
        return "PAY_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    // Generate a unique order ID
    public static String generateOrderId() {
        return "ORDER_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    // Example status methods
    public static boolean isSuccessStatus(String code) {
        return "2".equals(code);
    }

    public static boolean isFailedStatus(String code) {
        return "-1".equals(code) || "-2".equals(code);
    }

    public static boolean isPendingStatus(String code) {
        return "0".equals(code) || "1".equals(code);
    }

    public static String getStatusDescription(String code) {
        switch (code) {
            case "2": return "Payment completed successfully";
            case "1": return "Payment pending";
            case "-1": return "Payment cancelled by customer";
            case "-2": return "Payment failed";
            default: return "Unknown status: " + code;
        }
    }
}
