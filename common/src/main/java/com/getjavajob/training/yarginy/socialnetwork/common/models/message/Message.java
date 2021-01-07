package com.getjavajob.training.yarginy.socialnetwork.common.models.message;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.sql.Timestamp;

public interface Message extends Entity {
    Account getAuthor();

    void setAuthor(Account author);

    String getText();

    void setText(String text);

    byte[] getImage();

    void setImage(byte[] image);

    Timestamp getDate();

    void setDate(Timestamp date);

    long getReceiverId();

    void setReceiverId(long receiverId);
}
