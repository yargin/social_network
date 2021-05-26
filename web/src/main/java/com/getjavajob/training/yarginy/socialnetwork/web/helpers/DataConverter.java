package com.getjavajob.training.yarginy.socialnetwork.web.helpers;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;

import static java.util.Objects.isNull;

@Component("dataConverter")
public class DataConverter implements Serializable {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

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

    public String getStringDate(Timestamp date) {
        return dateFormat.format(date);
    }
}
