package com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages;

import java.io.Serializable;

public abstract class MessagesTable implements Serializable {
    public final String table;
    public final String id;
    public final String author;
    public final String message;
    public final String image;
    public final String receiverId;
    public final String posted;

    protected MessagesTable() {
        table = getTableName();
        id = table + '.' + "Id";
        author = table + '.' + "author";
        message = table + '.' + "message";
        image = table + '.' + "image";
        receiverId = table + '.' + "receiver_id";
        posted = table + '.' + "posted";
    }

    protected abstract String getTableName();
}
