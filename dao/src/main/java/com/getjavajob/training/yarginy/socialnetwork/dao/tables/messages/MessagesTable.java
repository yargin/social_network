package com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages;

public abstract class MessagesTable {
    public final String table;
    public final String id;
    public final String author;
    public final String message;
    public final String image;
    public final String receiverId;
    public final String posted;

    public MessagesTable() {
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
