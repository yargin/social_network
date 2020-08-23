package com.getjavajob.training.yarginy.socialnetwork.common.entities.account;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.additionaldata.Sex;

import java.time.LocalDate;

/**
 * provides object model of relational entity Account
 */
public interface Account extends Entity {
    String getName();

    String getSurname();

    String getPatronymic();

    Sex getSex();

    LocalDate getBirthDate();

    String getEmail();

    String getAdditionalEmail();

    String getIcq();

    String getSkype();

    String getCity();

    String getCountry();

    void setSex(Sex sex);

    void setName(String name);

    void setSurname(String surname);

    void setPatronymic(String patronymic);

    void setBirthDate(LocalDate birthDate);

    void setEmail(String email);

    void setAdditionalEmail(String additionalEmail);

    void setIcq(String icq);

    void setSkype(String skype);

    void setCity(String city);

    void setCountry(String country);
}
