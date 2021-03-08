package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.util.Objects.isNull;

@Component("dataHandler")
public class DataHandler implements Serializable {
    private static final int MAX_PHOTO_SIZE = 16000000;

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

    public byte[] readAvatarPhoto(InputStream inputStream) {
        return readFile(inputStream, MAX_PHOTO_SIZE);
    }

    public byte[] readMessageImage(InputStream inputStream) {
        return readFile(inputStream, MAX_PHOTO_SIZE);
    }

    public byte[] readFile(InputStream inputStream, int maxSize) {
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

    public String getHtmlPhoto(byte[] bytes) {
        if (!isNull(bytes)) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }
}
