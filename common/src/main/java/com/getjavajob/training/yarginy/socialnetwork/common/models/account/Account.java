package com.getjavajob.training.yarginy.socialnetwork.common.models.account;

import com.getjavajob.training.yarginy.socialnetwork.common.models.AbstractEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.stringOptional;
import static java.util.Objects.isNull;

@Component("account")
@Scope("prototype")
public class Account extends AbstractEntity implements Entity {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private String patronymic;
    private Sex sex;
    @Past
    private Date birthDate;
    private Date registrationDate;
    @Email
    private String email;
    @Email
    private String additionalEmail;
    private Role role;
    private String icq;
    private String skype;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    private byte[] photo;

    public Account() {
    }

    public Account(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Account(String name, String surname, String email) {
        this(name, email);
        this.surname = surname;
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
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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
        this.birthDate = birthDate;
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
        this.email = email;
    }

    public String getAdditionalEmail() {
        return additionalEmail;
    }

    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
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
        this.icq = icq;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        if (!isNull(photo)) {
            this.photo = Arrays.copyOf(photo, photo.length);
        }
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
