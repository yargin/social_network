package com.getjavajob.training.yarginy.socialnetwork.dao.tables;

public final class AccountsTable {
    public static final String TABLE = "Accounts";
    public static final String ID = "Id";
    public static final String NAME = "Name";
    public static final String SURNAME = "Surname";
    public static final String PATRONYMIC = "Patronymic";
    public static final String SEX = "Sex";
    public static final String BIRTH_DATE = "Birth_date";
    public static final String ICQ = "Icq";
    public static final String SKYPE = "Skype";
    public static final String EMAIL = "Email";
    public static final String ADDITIONAL_EMAIL = "Additional_email";
    public static final String COUNTRY = "Country";
    public static final String CITY = "City";
    public static final String REGISTRATION_DATE = "Registration_date";
    public static final String ROLE = "Role";
    public static final String PHOTO = "Photo";
    public static final String VIEW_FIELDS = ID + ", " + NAME + ", " + SURNAME + ", " + EMAIL;
    public static final String[] FIELDS = {"name", "surname", "patronymic", "sex", "birth_date", "icq",
            "skype", "email", "additional_email", "country", "city", "registration_date", "role", "photo"};

    private AccountsTable() {
    }

    public static String getViewFieldsWithAlias(String alias) {
        return alias + ".id id" + alias + ", " +
                alias + ".name name" + alias + ", " +
                alias + ".surname surname" + alias + ", " +
                alias + ".email email" + alias;
    }
}
