package com.getjavajob.training.yarginy.socialnetwork.common.models.message;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;

@Component
@Scope("prototype")
public class MessageImpl implements Message {
    private long id;
    private AccountImpl author;
    private String text;
    private byte[] image;
    private Timestamp date;
    private long receiverId;

    public MessageImpl() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountImpl getAuthor() {
        return author;
    }

    public void setAuthor(AccountImpl author) {
        this.author = DataCheckHelper.objectMandatory(author);
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
