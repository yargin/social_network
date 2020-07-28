package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.additionaldata.Sex;

import java.time.LocalDate;

public interface AccountDTO {
    int getId();

    String getName();

    String getSurname();

    String getPatronymic();

    Sex getSex();

    LocalDate getBirthDate();

    String getPhone();

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

    void setPhone(String phone);

    void setWorkPhone(String workPhone);

    void setHomeAddress(String homeAddress);

    void setWorkAddress(String workAddress);

    void setEmail(String email);

    void setAdditionalEmail(String additionalEmail);

    void setIcq(String icq);

    void setSkype(String skype);

    void setCity(String city);

    void setCountry(String country);
}
