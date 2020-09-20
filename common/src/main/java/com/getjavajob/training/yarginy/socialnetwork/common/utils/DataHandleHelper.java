package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataHandleHelper {
    public static String encrypt(String stringToEncrypt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        byte[] hashedPassword = md.digest(stringToEncrypt.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (byte symbol : hashedPassword) {
            stringBuilder.append(String.format("%02x", symbol));
        }
        return stringBuilder.toString();
    }
}
