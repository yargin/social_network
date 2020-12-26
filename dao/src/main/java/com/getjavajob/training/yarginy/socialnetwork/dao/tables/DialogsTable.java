package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class DialogsTable extends AbstractTable {
    public static final String TABLE = "dialogs";
    public static final String ID = "id";
    public static final String FIRST_ID = "first_id";
    public static final String SECOND_ID = "second_id";

    public DialogsTable() {
        super(null);
    }

    public DialogsTable(String alias) {
        super(alias);
    }

    @Override
    protected String[] getFieldsList() {
        return new String[]{ID, FIRST_ID, SECOND_ID};
    }

    @Override
    protected String[] getViewFieldsList() {
        return new String[]{ID};
    }

    @Override
    public String getTable() {
        return TABLE;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{ID};
    }

    @Override
    public String[] getAltKeys() {
        return new String[]{FIRST_ID, SECOND_ID};
    }
}
