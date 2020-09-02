package com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts;

/**
 * table Accounts columns
 */
public final class AccountsTable {
    public static final String TABLE = "accounts";
    public static final String ID = TABLE + '.' + "id";
    public static final String NAME = TABLE + '.' + "name";
    public static final String SURNAME = TABLE + '.' + "surname";
    public static final String PATRONYMIC = TABLE + '.' + "patronymic";
    public static final String SEX = TABLE + '.' + "sex";
    public static final String BIRTH_DATE = TABLE + '.' + "birth_date";
    public static final String ICQ = TABLE + '.' + "icq";
    public static final String SKYPE = TABLE + '.' + "skype";
    public static final String EMAIL = TABLE + '.' + "email";
    public static final String ADDITIONAL_EMAIL = TABLE + '.' + "additional_email";
    public static final String COUNTRY = TABLE + '.' + "country";
    public static final String CITY = TABLE + '.' + "city";

    private AccountsTable() {
    }
}
