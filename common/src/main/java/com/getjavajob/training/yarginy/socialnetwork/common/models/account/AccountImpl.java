package com.getjavajob.training.yarginy.socialnetwork.common.models.account;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.AbstractEntity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.additionaldata.Sex;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataHandleHelper;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.Date;
import java.util.Base64;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.*;
import static java.util.Objects.isNull;

@Component
public class AccountImpl extends AbstractEntity implements Account {
    private static final int MAX_PHOTO_SIZE = 16000000;
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

    public AccountImpl() {
    }

    public AccountImpl(String name, String email) {
        this.name = stringMandatory(name);
        this.email = emailMandatory(email);
    }

    public AccountImpl(String name, String surname, String email) {
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = stringMandatory(name);
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = stringMandatory(surname);
    }

    @Override
    public String getPatronymic() {
        return patronymic;
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
    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public void setBirthDate(Date birthDate) {
        this.birthDate = eligibleAge(birthDate);
    }

    @Override
    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = emailMandatory(email);
    }

    @Override
    public String getAdditionalEmail() {
        return additionalEmail;
    }

    @Override
    public void setAdditionalEmail(String additionalEmail) {
        if (Objects.equals(additionalEmail, email)) {
            throw new IncorrectDataException(IncorrectData.SAME_ADDITIONAL_EMAIL);
        }
        this.additionalEmail = emailOptional(additionalEmail);
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
        return icq;
    }

    @Override
    public void setIcq(String icq) {
        this.icq = stringOptional(icq);
    }

    @Override
    public String getSkype() {
        return skype;
    }

    @Override
    public void setSkype(String skype) {
        this.skype = stringOptional(skype);
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = stringOptional(city);
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = stringOptional(country);
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public void setPhoto(InputStream photo) {
        this.photo = DataHandleHelper.readFile(photo, MAX_PHOTO_SIZE);
    }

    @Override
    public String getHtmlPhoto() {
        if (!isNull(photo)) {
            return Base64.getEncoder().encodeToString(photo);
        }
        return "";
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
