package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class PhonesTable extends AbstractTable {
    public static final String TABLE = "phones";
    public static final String ID = "id";
    public static final String NUMBER = "number";
    public static final String TYPE = "type";
    public static final String OWNER = "owner_id";
    public static final String[] FIELDS = new String[]{ID, NUMBER, TYPE, OWNER};
    public static final String[] VIEW_FIELDS = new String[]{ID, NUMBER, TYPE};

    public PhonesTable() {
        super("");
    }

    public PhonesTable(String alias) {
        super(alias);
    }

    @Override
    protected String[] getFieldsList() {
        return new String[]{ID, NUMBER, TYPE, OWNER};
    }

    @Override
    protected String[] getViewFieldsList() {
        return new String[0];
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
        return new String[]{NUMBER};
    }
}
