package com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts;

/**
 * table Accounts columns
 */
public interface Accounts {
    String TABLE = "Accounts";
    String ID = TABLE + '.' + "ID";
    String NAME = TABLE + '.' + "Name";
    String SURNAME = TABLE + '.' + "Surname";
    String PATRONYMIC = TABLE + '.' + "Patronymic";
    String SEX = TABLE + '.' + "sex";
    String BIRTH_DATE = TABLE + '.' + "Birth_date";
    String PHONE = TABLE + '.' + "Phone";
    String ADDITIONAL_PHONE = TABLE + '.' + "Additional_phone";
    String ICQ = TABLE + '.' + "Icq";
    String SKYPE = TABLE + '.' + "Skype";
    String EMAIL = TABLE + '.' + "Email";
    String ADDITIONAL_EMAIL = TABLE + '.' + "Additional_email";
    String COUNTRY = TABLE + '.' + "Country";
    String CITY = TABLE + '.' + "City";
}
