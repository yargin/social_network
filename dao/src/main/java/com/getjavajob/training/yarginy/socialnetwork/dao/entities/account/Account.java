package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Entity;

import java.time.LocalDate;

public interface Account extends Entity {
    int getId();

    String getName();

    String getSurname();

    String getPatronymic();

    Sex getSex();

    LocalDate getBirthDate();

    String getHomePhoneNumber();

    String getWorkPhoneNumber();

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

    void setHomePhoneNumber(String homePhoneNumber);

    void setWorkPhoneNumber(String workPhoneNumber);

    void setHomeAddress(String homeAddress);

    void setWorkAddress(String workAddress);

    void setEmail(String email);

    void setAdditionalEmail(String additionalEmail);

    void setIcq(String icq);

    void setSkype(String skype);

    void setCity(String city);

    void setCountry(String country);

    interface AccountBuilder extends Builder<Account> {
        Account id(int id);

        Account surname(String surname);

        Account name(String name);

        Account patronymic(String patronymic);

        Account sex(Sex sex);

        Account birthDate(LocalDate birthDate);

        Account homePhoneNumber(String homePhoneNumber);

        Account workPhoneNumber(String workPhoneNumber);

        Account homeAddress(String homeAddress);

        Account workAddress(String workAddress);

        Account email(String email);

        Account additionalEmail(String additionalEmail);

        Account icq(String icq);

        Account skype(String skype);

        Account city(String city);

        Account country(String country);

        @Override
        Account build();
    }
}
