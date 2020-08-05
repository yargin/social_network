package com.getjavajob.training.yarginy.socialnetwork.dao.models.account;

import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AccountImpl implements Account {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private Sex sex;
    private LocalDate birthDate;
    private String phone;
    private String additionalPhone;
    private String email;
    private String additionalEmail;
    private String icq;
    private String skype;
    private String city;
    private String country;
    private List<Group> groupsOwner;
    private List<Group> groupsMember;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getIdentifier() {
        return getEmail();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public Sex getSex() {
        return sex;
    }

    @Override
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getAdditionalPhone() {
        return additionalPhone;
    }

    @Override
    public void setAdditionalPhone(String additionalPhone) {
        this.additionalPhone = additionalPhone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getAdditionalEmail() {
        return additionalEmail;
    }

    @Override
    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }

    @Override
    public String getIcq() {
        return icq;
    }

    @Override
    public void setIcq(String icq) {
        this.icq = icq;
    }

    @Override
    public String getSkype() {
        return skype;
    }

    @Override
    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public List<Group> getGroupsOwner() {
        return groupsOwner;
    }

    @Override
    public void setGroupsOwner(List<Group> groupsOwner) {
        this.groupsOwner = groupsOwner;
    }

    @Override
    public List<Group> getGroupsMember() {
        return groupsMember;
    }

    @Override
    public void setGroupsMember(List<Group> groupsMember) {
        this.groupsMember = groupsMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(email, account.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Account{name=" + name + ", surname='" + surname + ", patronymic=" + patronymic + ", birthDate=" +
                birthDate + ", city=" + city + ", country=" + country + '}';
    }
}
