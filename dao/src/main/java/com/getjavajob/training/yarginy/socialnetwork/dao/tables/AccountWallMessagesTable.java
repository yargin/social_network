package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class AccountWallMessagesTable {
    public static final String TABLE = "Account_wall_messages";
    public static final String ID = TABLE + '.' + "Id";
    public static final String AUTHOR = TABLE + '.' + "author";
    public static final String MESSAGE = TABLE + '.' + "message";
    public static final String IMAGE = TABLE + '.' + "image";
    public static final String ACCOUNT_WALL_ID = TABLE + '.' + "account_wall_id";
    public static final String POSTED = TABLE + '.' + "posted";

    private AccountWallMessagesTable() {
    }
}
