package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity.Sex.valueOf;

public class AccountImpl implements Account {
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM " + TABLE + " WHERE " + EMAIL + " = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO " + TABLE + "(" + NAME + ", " + SURNAME + ", " +
            PATRONYMIC + ", " + BIRTH_DATE + ", " + SEX + ", " + HOME_PHONE + ", " + WORK_PHONE + ", " +
            HOME_ADDRESS + ", " + WORK_ADDRESS + ", " + ICQ + ", " + SKYPE + ", " + EMAIL + ", " + ADDITIONAL_EMAIL +
            ", " + COUNTRY + ", " + CITY + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "UPDATE " + TABLE + " SET " + NAME + " = ? " + SURNAME + " = ? " +
            PATRONYMIC + " = ? " + BIRTH_DATE + " = ? " + SEX + " = ? " + HOME_PHONE + " = ? " + WORK_PHONE + " = ? " +
            HOME_ADDRESS + " = ? " + WORK_ADDRESS + " = ? " + ICQ + " = ? " + SKYPE + " = ? " + EMAIL + " = ? " +
            ADDITIONAL_EMAIL + " = ? " + COUNTRY + " = ? " + CITY + " = ? WHERE" + ID + " = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM " + TABLE + " WHERE " + ID + " = ?";
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

    public AccountImpl(int id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            initAccount(statement);
        }
    }

    public AccountImpl(String email, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            initAccount(statement);
        }
    }

    private Account initAccount(PreparedStatement statement) throws SQLException {
        if (statement.execute()) {
            ResultSet rs = statement.getResultSet();
            id = rs.getInt(ID);
            name = rs.getString(NAME);
            surname = rs.getString(SURNAME);
            patronymic = rs.getString(PATRONYMIC);
            sex = valueOf(rs.getString(SEX));
            birthDate = rs.getDate(BIRTH_DATE).toLocalDate();
            homeAddress = rs.getString(HOME_ADDRESS);
            workAddress = rs.getString(WORK_ADDRESS);
            homePhoneNumber = rs.getString(HOME_PHONE);
            workPhoneNumber = rs.getString(WORK_PHONE);
            email = rs.getString(EMAIL);
            additionalEmail = rs.getString(ADDITIONAL_EMAIL);
            icq = rs.getString(ICQ);
            skype = rs.getString(SKYPE);
            email = rs.getString(EMAIL);
            city = rs.getString(CITY);
            country = rs.getString(COUNTRY);
        }
        throw new SQLException("Account not found");
    }

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
    public String getHomePhone() {
        return homePhoneNumber;
    }

    @Override
    public String getWorkPhone() {
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
    public void setHomePhone(String homePhone) {
        this.homePhoneNumber = homePhone;
    }

    @Override
    public void setWorkPhone(String workPhone) {
        this.workPhoneNumber = workPhone;
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
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
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

    @Override
    public boolean create(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, patronymic);
            statement.setDate(4, Date.valueOf(birthDate));
            statement.setString(5, sex.toString());
            statement.setString(6, homePhoneNumber);
            statement.setString(7, workPhoneNumber);
            statement.setString(8, homeAddress);
            statement.setString(9, workAddress);
            statement.setString(10, icq);
            statement.setString(11, skype);
            statement.setString(12, email);
            statement.setString(13, additionalEmail);
            statement.setString(14, country);
            statement.setString(15, city);
            return statement.execute();
        }
    }

    @Override
    public boolean update(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, patronymic);
            statement.setDate(4, Date.valueOf(birthDate));
            statement.setString(5, sex.toString());
            statement.setString(6, homePhoneNumber);
            statement.setString(7, workPhoneNumber);
            statement.setString(8, homeAddress);
            statement.setString(9, workAddress);
            statement.setString(10, icq);
            statement.setString(11, skype);
            statement.setString(12, email);
            statement.setString(13, additionalEmail);
            statement.setString(14, country);
            statement.setString(15, city);
            statement.setInt(16, id);
            return statement.execute();
        }
    }

    @Override
    public boolean delete(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT)) {
            statement.setInt(1, id);
            return statement.execute();
        }
    }

    public static class AccountBuilderImpl implements AccountBuilder {
        private final AccountImpl account = null;

//        public AccountBuilderImpl() {
//            account = new AccountImpl();
//        }

        public Account build() {
            return account;
        }

        @Override
        public AccountBuilder id(int id) {
            account.id = id;
            return this;
        }

        @Override
        public AccountBuilder surname(String surname) {
            account.surname = surname;
            return this;
        }

        @Override
        public AccountBuilder name(String name) {
            account.name = name;
            return this;
        }

        @Override
        public AccountBuilder patronymic(String patronymic) {
            account.patronymic = patronymic;
            return this;
        }

        @Override
        public AccountBuilder sex(Sex sex) {
            account.sex = sex;
            return this;
        }

        @Override
        public AccountBuilder birthDate(LocalDate birthDate) {
            account.birthDate = birthDate;
            return this;
        }

        @Override
        public AccountBuilder homePhone(String homePhone) {
            account.homePhoneNumber = homePhone;
            return this;
        }

        @Override
        public AccountBuilder workPhone(String workPhone) {
            account.workPhoneNumber = workPhone;
            return this;
        }

        @Override
        public AccountBuilder homeAddress(String homeAddress) {
            account.homeAddress = homeAddress;
            return this;
        }

        @Override
        public AccountBuilder workAddress(String workAddress) {
            account.workAddress = workAddress;
            return this;
        }

        @Override
        public AccountBuilder email(String email) {
            account.email = email;
            return this;
        }

        @Override
        public AccountBuilder additionalEmail(String additionalEmail) {
            account.additionalEmail = additionalEmail;
            return this;
        }

        @Override
        public AccountBuilder icq(String icq) {
            account.icq = icq;
            return this;
        }

        @Override
        public AccountBuilder skype(String skype) {
            account.skype = skype;
            return this;
        }

        @Override
        public AccountBuilder city(String city) {
            account.city = city;
            return this;
        }

        @Override
        public AccountBuilder country(String country) {
            account.country = country;
            return this;
        }
    }
}
