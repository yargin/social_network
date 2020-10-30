package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

/**
 * table Friendships_requests columns
 */
public final class FriendshipsRequestsTable {
    public static final String TABLE = "Friendships_requests";
    public static final String REQUESTER = TABLE + '.' + "requester";
    public static final String RECEIVER = TABLE + '.' + "receiver";

    private FriendshipsRequestsTable() {
    }
}
