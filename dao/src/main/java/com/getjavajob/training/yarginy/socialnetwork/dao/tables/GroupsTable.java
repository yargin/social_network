package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class GroupsTable extends AbstractTable {
    public static final String TABLE = "_groups";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String OWNER = "owner_id";
    public static final String CREATION_DATE = "creation_date";
    public static final String PHOTO = "photo";

    public GroupsTable(String alias) {
        super(alias);
    }

    public GroupsTable() {
        this(null);
    }

    @Override
    protected String[] getFieldsList() {
        return new String[]{ID, NAME, DESCRIPTION, OWNER, CREATION_DATE, PHOTO};
    }

    @Override
    protected String[] getViewFieldsList() {
        return new String[]{ID, NAME};
    }

    @Override
    protected String getTableName() {
        return TABLE;
    }
}
