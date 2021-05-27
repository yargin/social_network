package com.getjavajob.training.yarginy.socialnetwork.common.models;

import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Sex;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

@Component
@Scope("prototype")
@Entity
@Table(name = "accounts")
@NamedEntityGraph(name = "graph.Account.allProperties", includeAllAttributes = true)
public class Account implements Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    private String patronymic;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private Date birthDate;
    private Date registrationDate;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true)
    private String additionalEmail;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String icq;
    private String skype;
    private String city;
    private String country;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;
    @Version
    private long version;

    public Account() {
    }

    public Account(long id) {
        this.id = id;
    }

    public Account(String name, String email) {
        this(0, name, null, email);
    }

    public Account(String name, String surname, String email) {
        this(0, name, surname, email);
    }

    public Account(long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
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
            return Objects.equals(email, account.getEmail());
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
