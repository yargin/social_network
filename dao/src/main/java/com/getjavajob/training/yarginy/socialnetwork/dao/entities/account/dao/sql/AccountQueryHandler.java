package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDTO;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dto.AccountDTOImpl;

import java.sql.*;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.additionaldata.Sex.valueOf;
import static java.util.Objects.isNull;

public class AccountQueryHandler {
    private static final String TABLE = "Accounts";
    private static final String ID = "ID";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final String PATRONYMIC = "Patronymic";
    private static final String SEX = "sex";
    private static final String BIRTH_DATE = "Birth_date";
    private static final String PHONE = "Phone";
    private static final String WORK_PHONE = "Work_phone";
    private static final String HOME_ADDRESS = "Home_address";
    private static final String WORK_ADDRESS = "Work_address";
    private static final String ICQ = "Icq";
    private static final String SKYPE = "Skype";
    private static final String EMAIL = "Email";
    private static final String ADDITIONAL_EMAIL = "Additional_email";
    private static final String COUNTRY = "Country";
    private static final String CITY = "City";
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM " + TABLE + " WHERE " + EMAIL + " = ?";

    public static void buildInsert(ResultSet resultSet, AccountDTO account) throws SQLException {
        resultSet.updateString(NAME, account.getName());
        resultSet.updateString(SURNAME, account.getSurname());
        resultSet.updateString(PATRONYMIC, account.getPatronymic());
        resultSet.updateDate(BIRTH_DATE, Date.valueOf(account.getBirthDate()));
        resultSet.updateString(PHONE, account.getPhone());
        resultSet.updateString(WORK_PHONE, account.getWorkPhone());
        resultSet.updateString(HOME_ADDRESS, account.getHomeAddress());
        resultSet.updateString(WORK_ADDRESS, account.getWorkAddress());
        resultSet.updateString(ICQ, account.getIcq());
        resultSet.updateString(SKYPE, account.getSkype());
        resultSet.updateString(EMAIL, account.getEmail());
        resultSet.updateString(ADDITIONAL_EMAIL, account.getAdditionalEmail());
        resultSet.updateString(COUNTRY, account.getCountry());
        resultSet.updateString(CITY, account.getCity());
    }

    public static ResultSet getResultSetByEmail(Connection connection, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            return statement.executeQuery();
        }
    }

    public static ResultSet getResultSetById(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeQuery();
        }
    }

    public static AccountDTO selectById(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            return selectAccount(statement);
        }
    }

    public static AccountDTO selectByEmail(Connection connection, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            return selectAccount(statement);
        }
    }

    private static AccountDTO selectAccount(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.executeQuery()) {
            if (isNull(rs)) {
                throw new SQLException("Account not found");
            }
            AccountDTO accountDTO = new AccountDTOImpl();
            accountDTO.setId(rs.getInt(ID));
            accountDTO.setName(rs.getString(NAME));
            accountDTO.setSurname(rs.getString(SURNAME));
            accountDTO.setPatronymic(rs.getString(PATRONYMIC));
            accountDTO.setSex(valueOf(rs.getString(SEX)));
            accountDTO.setBirthDate(rs.getDate(BIRTH_DATE).toLocalDate());
            accountDTO.setHomeAddress(rs.getString(HOME_ADDRESS));
            accountDTO.setWorkAddress(rs.getString(WORK_ADDRESS));
            accountDTO.setPhone(rs.getString(PHONE));
            accountDTO.setWorkPhone(rs.getString(WORK_PHONE));
            accountDTO.setEmail(rs.getString(EMAIL));
            accountDTO.setAdditionalEmail(rs.getString(ADDITIONAL_EMAIL));
            accountDTO.setIcq(rs.getString(ICQ));
            accountDTO.setSkype(rs.getString(SKYPE));
            accountDTO.setCity(rs.getString(CITY));
            accountDTO.setCountry(rs.getString(COUNTRY));
            return accountDTO;
        }
    }
}
