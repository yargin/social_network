package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class AccountsTable {
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
    public static final String VIEW_FIELDS = ID + ", " + NAME + ", " + SURNAME + ", " + EMAIL;
    public static final String[] FIELDS = {"name", "surname", "patronymic", "sex", "birth_date", "icq",
            "skype", "email", "additional_email", "country", "city", "registration_date", "role", "photo"};

    private AccountsTable() {
    }

    public static String getViewFieldsWithPostFix(String alias) {
        return alias + ".id id" + alias + ", " +
                alias + ".name name" + alias + ", " +
                alias + ".surname surname" + alias + ", " +
                alias + ".email email" + alias;
    }
}
