package com.getjavajob.training.yarginy.socialnetwork.web.controllers.datakeepers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.AccountWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.DialogMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.GroupWallMessage;
import com.getjavajob.training.yarginy.socialnetwork.common.models.messages.Message;

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

    private void mapMessageAttributes(Message<? extends Model> message) {
        message.setId(id);
        message.setText(text);
        message.setImage(image);
        message.setDate(date);
    }

    public AccountWallMessage getAccountWallMessage(long authorId, long receiverId) {
        AccountWallMessage message = new AccountWallMessage();
        mapMessageAttributes(message);
        message.setAuthor(new Account(authorId));
        message.setReceiver(new Account(receiverId));
        return message;
    }

    public GroupWallMessage getGroupWallMessage(long authorId, long receiverId) {
        GroupWallMessage message = new GroupWallMessage();
        mapMessageAttributes(message);
        message.setAuthor(new Account(authorId));
        message.setReceiver(new Group(receiverId));
        return message;
    }

    public DialogMessage getDialogMessage(long authorId, long dialogId) {
        DialogMessage message = new DialogMessage();
        mapMessageAttributes(message);
        message.setAuthor(new Account(authorId));
        message.setReceiver(new Dialog(dialogId));
        return message;
    }
}
