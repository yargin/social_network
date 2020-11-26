package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.io.IOException;
import java.io.InputStream;
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

    public static byte[] readFile(InputStream inputStream, int maxSize) {
        try {
            int size = inputStream.available();
            if (size > maxSize) {
                throw new IncorrectDataException(IncorrectData.FILE_TOO_LARGE);
            }
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
