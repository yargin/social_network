package com.getjavajob.training.yarginy.socialnetwork.common.models.account;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.AbstractEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.*;
import static java.util.Objects.isNull;

@Component("account")
@Scope("prototype")
public class Account extends AbstractEntity implements Entity {
    private String name;
    private String surname;
    private String patronymic;
    private Sex sex;
    private Date birthDate;
    private Date registrationDate;
    private String email;
    private String additionalEmail;
    private Role role;
    private String icq;
    private String skype;
    private String city;
    private String country;
    private byte[] photo;

    public Account() {
    }

    public Account(String name, String email) {
        this.name = stringMandatory(name);
        this.email = emailMandatory(email);
    }

    public Account(String name, String surname, String email) {
        this(name, email);
        this.surname = stringMandatory(surname);
    }

    @Override
    public long getId() {
        return getIdNumber();
    }

    @Override
    public void setId(long id) {
        setIdNumber(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = stringMandatory(name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = stringMandatory(surname);
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = stringOptional(patronymic);
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = eligibleAge(birthDate);
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = emailMandatory(email);
    }

    public String getAdditionalEmail() {
        return additionalEmail;
    }

    public void setAdditionalEmail(String additionalEmail) {
        if (Objects.equals(additionalEmail, email)) {
            throw new IncorrectDataException(IncorrectData.SAME_ADDITIONAL_EMAIL);
        }
        this.additionalEmail = emailOptional(additionalEmail);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = stringOptional(icq);
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = stringOptional(skype);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = stringOptional(city);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = stringOptional(country);
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = Arrays.copyOf(photo, photo.length);
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
