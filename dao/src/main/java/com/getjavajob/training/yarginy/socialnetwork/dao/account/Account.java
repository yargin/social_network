package com.getjavajob.training.yarginy.socialnetwork.dao.account;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dtoimplementation.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;

import java.time.LocalDate;
import java.util.List;

/**
 * provides object model of relational entity Account
 */
public interface Account {
    /**
     * notifies that current {@link Account} doesn't exist
     *
     * @return representation of non-existing {@link Account}
     */
    static Account getNullAccount() {
        Account nullAccount = new AccountImpl();
        nullAccount.setEmail("account not found");
        return nullAccount;
    }

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

    List<Group> getGroupsOwner();

    void setGroupsOwner(List<Group> groupsOwner);

    List<Group> getGroupsMember();

    void setGroupsMember(List<Group> groupsMember);
}
