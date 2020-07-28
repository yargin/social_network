package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDTO;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dto.AccountDTOImpl;

import java.sql.*;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql.AccountColumns.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.additionaldata.Sex.valueOf;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql.ConnectionFactory.getConnection;
import static java.util.Objects.isNull;

public class AccountDAOImpl implements AccountDAO {
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

    public AccountDTO selectAccount(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            return selectAccount(statement);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    public AccountDTO selectAccount(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            return selectAccount(statement);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    private AccountDTO selectAccount(PreparedStatement statement) throws SQLException {
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

    @Override
    public boolean createAccount(AccountDTO account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT)) {
            setStatementValues(statement, account);
            return statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public boolean updateAccount(AccountDTO account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)) {
            setStatementValues(statement, account);
            statement.setInt(16, account.getId());
            return statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    private void setStatementValues(PreparedStatement statement, AccountDTO account) throws SQLException {
        statement.setString(1, account.getName());
        statement.setString(2, account.getSurname());
        statement.setString(3, account.getPatronymic());
        statement.setDate(4, Date.valueOf(account.getBirthDate()));
        statement.setString(5, account.getSex().toString());
        statement.setString(6, account.getPhone());
        statement.setString(7, account.getWorkPhone());
        statement.setString(8, account.getHomeAddress());
        statement.setString(9, account.getWorkAddress());
        statement.setString(10, account.getIcq());
        statement.setString(11, account.getSkype());
        statement.setString(12, account.getEmail());
        statement.setString(13, account.getAdditionalEmail());
        statement.setString(14, account.getCountry());
        statement.setString(15, account.getCity());
    }

    @Override
    public boolean deleteAccount(AccountDTO account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT)) {
            statement.setInt(1, account.getId());
            return statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }
}
