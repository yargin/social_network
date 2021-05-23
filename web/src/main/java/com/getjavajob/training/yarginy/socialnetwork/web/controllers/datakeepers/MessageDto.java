package com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers;

import java.sql.Timestamp;
import java.util.Arrays;

import static java.util.Objects.isNull;

public class MessageDto {
    private long id;
    private String text;
    private byte[] image;
    private Timestamp date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        if (!isNull(image)) {
            this.image = Arrays.copyOf(image, image.length);
        }
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
