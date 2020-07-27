package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account;

import java.time.LocalDate;
import java.util.Objects;

public class AccountImpl implements Account {
    private int id;
    private Sex sex;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthDate;
    private String homePhoneNumber;
    private String workPhoneNumber;
    private String homeAddress;
    private String workAddress;
    private String email;
    private String additionalEmail;
    private String icq;
    private String skype;
    private String city;
    private String country;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public Sex getSex() {
        return sex;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    @Override
    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    @Override
    public String getHomeAddress() {
        return homeAddress;
    }

    @Override
    public String getWorkAddress() {
        return workAddress;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getAdditionalEmail() {
        return additionalEmail;
    }

    @Override
    public String getIcq() {
        return icq;
    }

    @Override
    public String getSkype() {
        return skype;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    @Override
    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    @Override
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }

    @Override
    public void setIcq(String icq) {
        this.icq = icq;
    }

    @Override
    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountImpl)) return false;
        AccountImpl account = (AccountImpl) o;
        return Objects.equals(email, account.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "AccountImpl{ name=" + name + ", surname='" + surname + ", patronymic=" + patronymic + ", birthDate=" +
                birthDate + ", city=" + city + ", country=" + country + '}';
    }

    public static AccountBuilder builder() {
        return new AccountBuilderImpl();
    }

    private static class AccountBuilderImpl implements AccountBuilder {
        private AccountImpl account;

        private AccountBuilderImpl() {
            account = new AccountImpl();
        }

        public Account build() {
            return account;
        }

        @Override
        public Account id(int id) {
            account.id = id;
            return account;
        }

        @Override
        public Account surname(String surname) {
            account.surname = surname;
            return account;
        }

        @Override
        public Account name(String name) {
            account.name = name;
            return account;
        }

        @Override
        public Account patronymic(String patronymic) {
            account.patronymic = patronymic;
            return account;
        }

        @Override
        public Account sex(Sex sex) {
            account.sex = sex;
            return account;
        }

        @Override
        public Account birthDate(LocalDate birthDate) {
            account.birthDate = birthDate;
            return account;
        }

        @Override
        public Account homePhoneNumber(String homePhoneNumber) {
            account.homePhoneNumber = homePhoneNumber;
            return account;
        }

        @Override
        public Account workPhoneNumber(String workPhoneNumber) {
            account.workPhoneNumber = workPhoneNumber;
            return account;
        }

        @Override
        public Account homeAddress(String homeAddress) {
            account.homeAddress = homeAddress;
            return account;
        }

        @Override
        public Account workAddress(String workAddress) {
            account.workAddress = workAddress;
            return account;
        }

        @Override
        public Account email(String email) {
            account.email = email;
            return account;
        }

        @Override
        public Account additionalEmail(String additionalEmail) {
            account.additionalEmail = additionalEmail;
            return account;
        }

        @Override
        public Account icq(String icq) {
            account.icq = icq;
            return account;
        }

        @Override
        public Account skype(String skype) {
            account.skype = skype;
            return account;
        }

        @Override
        public Account city(String city) {
            account.city = city;
            return account;
        }

        @Override
        public Account country(String country) {
            account.country = country;
            return account;
        }
    }
}
