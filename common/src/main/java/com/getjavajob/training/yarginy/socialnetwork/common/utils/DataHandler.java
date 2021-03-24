package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.util.Objects.isNull;

@Component("dataHandler")
public class DataHandler implements Serializable {
    public String encrypt(String stringToEncrypt) {
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

    public String getHtmlPhoto(byte[] bytes) {
        if (!isNull(bytes)) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }
}
