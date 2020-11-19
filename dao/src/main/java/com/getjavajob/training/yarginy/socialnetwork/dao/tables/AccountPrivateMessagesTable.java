package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class AccountPrivateMessagesTable {
    public static final String TABLE = "Account_private_messages";
    public static final String ID = TABLE + '.' + "Id";
    public static final String AUTHOR = TABLE + '.' + "author";
    public static final String MESSAGE = TABLE + '.' + "message";
    public static final String IMAGE = TABLE + '.' + "image";
    public static final String ACCOUNT_RECEIVER_ID = TABLE + '.' + "account_receiver_id";
    public static final String POSTED = TABLE + '.' + "posted";

    private AccountPrivateMessagesTable() {
    }
}