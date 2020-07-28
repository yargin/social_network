package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Builder;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public interface Account {
    String TABLE = "Accounts";
    String ID = "Id";
    String NAME = "Name";
    String SURNAME = "Surname";
    String PATRONYMIC = "Patronymic";
    String SEX = "Sex";
    String BIRTH_DATE = "Birth_date";
    String HOME_PHONE = "Home_phone";
    String WORK_PHONE = "Work_phone";
    String HOME_ADDRESS = "Home_address";
    String WORK_ADDRESS = "Work_address";
    String EMAIL = "Email";
    String ADDITIONAL_EMAIL = "Additional_email";
    String ICQ = "Icq";
    String SKYPE = "Skype";
    String CITY = "City";
    String COUNTRY = "Country";

    int getId();

    String getName();

    String getSurname();

    String getPatronymic();

    Sex getSex();

    LocalDate getBirthDate();

    String getHomePhone();

    String getWorkPhone();

    String getHomeAddress();

    String getWorkAddress();

    String getEmail();

    String getAdditionalEmail();

    String getIcq();

    String getSkype();

    String getCity();

    String getCountry();

    void setId(int id);

    void setSex(Sex sex);

    void setName(String name);

    void setSurname(String surname);

    void setPatronymic(String patronymic);

    void setBirthDate(LocalDate birthDate);

    void setHomePhone(String homePhone);

    void setWorkPhone(String workPhone);

    void setHomeAddress(String homeAddress);

    void setWorkAddress(String workAddress);

    void setEmail(String email);

    void setAdditionalEmail(String additionalEmail);

    void setIcq(String icq);

    void setSkype(String skype);

    void setCity(String city);

    void setCountry(String country);

    boolean create(Connection connection) throws SQLException;

    boolean update(Connection connection) throws SQLException;

    boolean delete(Connection connection) throws SQLException;

    interface AccountBuilder extends Builder<Account> {
        AccountBuilder id(int id);

        AccountBuilder surname(String surname);

        AccountBuilder name(String name);

        AccountBuilder patronymic(String patronymic);

        AccountBuilder sex(Sex sex);

        AccountBuilder birthDate(LocalDate birthDate);

        AccountBuilder homePhone(String homePhone);

        AccountBuilder workPhone(String workPhone);

        AccountBuilder homeAddress(String homeAddress);

        AccountBuilder workAddress(String workAddress);

        AccountBuilder email(String email);

        AccountBuilder additionalEmail(String additionalEmail);

        AccountBuilder icq(String icq);

        AccountBuilder skype(String skype);

        AccountBuilder city(String city);

        AccountBuilder country(String country);

        @Override
        Account build();
    }
}
