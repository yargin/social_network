package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dto;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.additionaldata.Sex;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.additionaldata.Sex.valueOf;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.sql.AccountColumns.*;
import static java.util.Objects.isNull;

public class AccountSQL implements Account {
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM " + TABLE + " WHERE " + EMAIL + " = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO " + TABLE + "(" + NAME + ", " + SURNAME + ", " +
            PATRONYMIC + ", " + BIRTH_DATE + ", " + SEX + ", " + PHONE + ", " + WORK_PHONE + ", " +
            HOME_ADDRESS + ", " + WORK_ADDRESS + ", " + ICQ + ", " + SKYPE + ", " + EMAIL + ", " + ADDITIONAL_EMAIL +
            ", " + COUNTRY + ", " + CITY + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "UPDATE " + TABLE + " SET " + NAME + " = ? " + SURNAME + " = ? " +
            PATRONYMIC + " = ? " + BIRTH_DATE + " = ? " + SEX + " = ? " + PHONE + " = ? " + WORK_PHONE + " = ? " +
            HOME_ADDRESS + " = ? " + WORK_ADDRESS + " = ? " + ICQ + " = ? " + SKYPE + " = ? " + EMAIL + " = ? " +
            ADDITIONAL_EMAIL + " = ? " + COUNTRY + " = ? " + CITY + " = ? WHERE" + ID + " = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM " + TABLE + " WHERE " + ID + " = ?";
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private Sex sex;
    private LocalDate birthDate;
    private String phone;
    private String workPhone;
    private String homeAddress;
    private String workAddress;
    private String email;
    private String additionalEmail;
    private String icq;
    private String skype;
    private String city;
    private String country;

    public AccountSQL(int id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            initAccount(statement);
        }
    }

    public AccountSQL(String email, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            initAccount(statement);
        }
    }

    private void initAccount(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.executeQuery()) {
            if (isNull(rs)) {
                throw new SQLException("Account not found");
            }
            id = rs.getInt(ID);
            name = rs.getString(NAME);
            surname = rs.getString(SURNAME);
            patronymic = rs.getString(PATRONYMIC);
            sex = valueOf(rs.getString(SEX));
            birthDate = rs.getDate(BIRTH_DATE).toLocalDate();
            homeAddress = rs.getString(HOME_ADDRESS);
            workAddress = rs.getString(WORK_ADDRESS);
            phone = rs.getString(PHONE);
            workPhone = rs.getString(WORK_PHONE);
            email = rs.getString(EMAIL);
            additionalEmail = rs.getString(ADDITIONAL_EMAIL);
            icq = rs.getString(ICQ);
            skype = rs.getString(SKYPE);
            email = rs.getString(EMAIL);
            city = rs.getString(CITY);
            country = rs.getString(COUNTRY);
        }
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
    public String getPhone() {
        return phone;
    }

    @Override
    public String getWorkPhone() {
        return workPhone;
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
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
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
            setStatementValues(statement);
            return statement.execute();
        }
    }

    @Override
    public boolean update(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)) {
            setStatementValues(statement);
            statement.setInt(16, id);
            return statement.execute();
        }
    }

    private void setStatementValues(PreparedStatement statement) throws SQLException {
        statement.setString(1, name);
        statement.setString(2, surname);
        statement.setString(3, patronymic);
        statement.setDate(4, Date.valueOf(birthDate));
        statement.setString(5, sex.toString());
        statement.setString(6, phone);
        statement.setString(7, workPhone);
        statement.setString(8, homeAddress);
        statement.setString(9, workAddress);
        statement.setString(10, icq);
        statement.setString(11, skype);
        statement.setString(12, email);
        statement.setString(13, additionalEmail);
        statement.setString(14, country);
        statement.setString(15, city);
        statement.setInt(16, id);
    }

    @Override
    public boolean delete(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT)) {
            statement.setInt(1, id);
            return statement.execute();
        }
    }
}
