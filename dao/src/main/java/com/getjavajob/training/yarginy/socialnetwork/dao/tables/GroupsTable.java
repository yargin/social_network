package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

/**
 * table Groups columns
 */
public final class GroupsTable {
    public static final String TABLE = "_Groups";
    public static final String ID = TABLE + '.' + "ID";
    public static final String NAME = TABLE + '.' + "Name";
    public static final String DESCRIPTION = TABLE + '.' + "Description";
    public static final String OWNER = TABLE + '.' + "Owner_id";
    public static final String CREATION_DATE = TABLE + '.' + "Creation_date";
    public static final String PHOTO = TABLE + '.' + "Photo";
    public static final String PHOTO_PREVIEW = TABLE + '.' + "Photo_preview";

    private GroupsTable() {
    }
}
