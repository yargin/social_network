package com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts;

/**
 * table Accounts columns
 */
public final class AccountsTable {
    public static final String TABLE = "Accounts";
    public static final String ID = TABLE + '.' + "ID";
    public static final String NAME = TABLE + '.' + "Name";
    public static final String SURNAME = TABLE + '.' + "Surname";
    public static final String PATRONYMIC = TABLE + '.' + "Patronymic";
    public static final String SEX = TABLE + '.' + "sex";
    public static final String BIRTH_DATE = TABLE + '.' + "Birth_date";
    public static final String ICQ = TABLE + '.' + "Icq";
    public static final String SKYPE = TABLE + '.' + "Skype";
    public static final String EMAIL = TABLE + '.' + "Email";
    public static final String ADDITIONAL_EMAIL = TABLE + '.' + "Additional_email";
    public static final String COUNTRY = TABLE + '.' + "Country";
    public static final String CITY = TABLE + '.' + "City";

    private AccountsTable() {
    }
}
