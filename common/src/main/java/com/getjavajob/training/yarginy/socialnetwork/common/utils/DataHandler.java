package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;

import static java.util.Objects.isNull;

@Component("dataHandler")
public class DataHandler implements Serializable {
    public String getHtmlPhoto(byte[] bytes) {
        if (!isNull(bytes)) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    public byte[] getBytesDecoded(String file) {
        if (!isNull(file) && !file.isEmpty()) {
            return Base64.getDecoder().decode(file);
        }
        return new byte[0];
    }
}
