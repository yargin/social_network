package com.getjavajob.training.yarginy.socialnetwork.common.entities.account;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.AbstractEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.additionaldata.Sex;

import java.time.LocalDate;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.*;
import static java.util.Objects.isNull;

public class AccountImpl extends AbstractEntity implements Account {
    private String name;
    private String surname;
    private String patronymic;
    private Sex sex;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private String email;
    private String additionalEmail;
    private String password;
    private Role role;
    private String icq;
    private String skype;
    private String city;
    private String country;

    public AccountImpl() {
    }

    public AccountImpl(String name, String email) {
        this.name = stringMandatory(name);
        this.email = emailMandatory(email);
    }

    @Override
    public long getId() {
        return getIdNumber();
    }

    @Override
    public String getIdentifier() {
        return getEmail();
    }

    @Override
    public void setId(long id) {
        setIdNumber(id);
    }

    @Override
    public String getName() {
        return stringMandatory(name);
    }

    @Override
    public void setName(String name) {
        this.name = stringMandatory(name);
    }

    @Override
    public String getSurname() {
        return stringMandatory(surname);
    }

    @Override
    public void setSurname(String surname) {
        this.surname = stringMandatory(surname);
    }

    @Override
    public String getPatronymic() {
        return stringOptional(patronymic);
    }

    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = stringOptional(patronymic);
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
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String getEmail() {
        return emailMandatory(email);
    }

    @Override
    public void setEmail(String email) {
        this.email = emailMandatory(email);
    }

    @Override
    public String getAdditionalEmail() {
        return emailOptional(additionalEmail);
    }

    @Override
    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = emailOptional(additionalEmail);
    }

    @Override
    public String getPassword() {
        return passwordMandatory(password);
    }

    @Override
    public void setPassword(String password) {
        this.password = passwordMandatory(password);
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getIcq() {
        return stringOptional(icq);
    }

    @Override
    public void setIcq(String icq) {
        this.icq = stringOptional(icq);
    }

    @Override
    public String getSkype() {
        return stringOptional(skype);
    }

    @Override
    public void setSkype(String skype) {
        this.skype = stringOptional(skype);
    }

    @Override
    public String getCity() {
        return stringOptional(city);
    }

    @Override
    public void setCity(String city) {
        this.city = stringOptional(city);
    }

    @Override
    public String getCountry() {
        return stringOptional(country);
    }

    @Override
    public void setCountry(String country) {
        this.country = stringOptional(country);
    }

    @Override
    public boolean equals(Object o) {
        if (isNull(o)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof Account) {
            Account account = (Account) o;
            return Objects.equals(stringOptional(email), stringOptional(account.getEmail()));
        }
        return false;
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
