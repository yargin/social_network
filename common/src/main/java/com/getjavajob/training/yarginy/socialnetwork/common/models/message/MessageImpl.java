package com.getjavajob.training.yarginy.socialnetwork.common.models.message;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Objects.isNull;

public class MessageImpl implements Message {
    private static final int MAX_PHOTO_SIZE = 16000000;
    private long id;
    private Account author;
    private String text;
    private byte[] image;
    private Timestamp date;
    private long receiverId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = DataCheckHelper.objectMandatory(author);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getHtmlImage() {
        if (!isNull(image) && image.length > 0) {
            return Base64.getEncoder().encodeToString(image);
        }
        return null;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public void setImage(InputStream photo) {
        try {
            int size = photo.available();
            if (size < 1) {
                return;
            }
            if (size > MAX_PHOTO_SIZE) {
                throw new IncorrectDataException(IncorrectData.FILE_TOO_LARGE);
            }
            this.image = new byte[photo.available()];
            photo.read(this.image);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void setImage(byte[] image) {
        this.image = Arrays.copyOf(image, image.length);
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }
}
