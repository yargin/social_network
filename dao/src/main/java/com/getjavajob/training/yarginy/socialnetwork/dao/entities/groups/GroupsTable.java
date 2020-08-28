package com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups;

/**
 * table Groups columns
 */
public final class GroupsTable {
    public static final String TABLE = "_Groups";
    public static final String ID = TABLE + '.' + "ID";
    public static final String NAME = TABLE + '.' + "Name";
    public static final String DESCRIPTION = TABLE + '.' + "Description";
    public static final String OWNER = TABLE + '.' + "Owner_id";

    private GroupsTable() {
    }
}
