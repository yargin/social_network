package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class AccountsTable extends AbstractTable {
    public static final String TABLE = "accounts";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PATRONYMIC = "patronymic";
    public static final String SEX = "sex";
    public static final String BIRTH_DATE = "birth_date";
    public static final String ICQ = "icq";
    public static final String SKYPE = "skype";
    public static final String EMAIL = "email";
    public static final String ADDITIONAL_EMAIL = "additional_email";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String REGISTRATION_DATE = "registration_date";
    public static final String ROLE = "role";
    public static final String PHOTO = "photo";
    public static final String[] FIELDS = {ID, NAME, SURNAME, PATRONYMIC, SEX, BIRTH_DATE, ICQ,
            SKYPE, EMAIL, ADDITIONAL_EMAIL, COUNTRY, CITY, REGISTRATION_DATE, ROLE, PHOTO};
    public static final String VIEW_FIELDS = "ID, NAME, SURNAME, EMAIL";
    public static final String[] VIEW_FIELDS2 = {ID, NAME, SURNAME, EMAIL};

    public AccountsTable(String alias) {
        super(alias);
    }

    public AccountsTable() {
        this(null);
    }

    @Override
    public String getTable() {
        return TABLE;
    }

    @Override
    protected String[] getFieldsList() {
        return FIELDS;
    }

    @Override
    public String[] getViewFieldsList() {
        return VIEW_FIELDS2;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{ID};
    }

    @Override
    public String[] getAltKeys() {
        return new String[]{EMAIL};
    }
}
