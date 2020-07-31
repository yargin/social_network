package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql.AccountSQLQueriesHandler.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql.ConnectionFactory.getConnection;

public class AccountDAOImpl implements AccountDAO {
    public Account selectAccount(int id) {
        try {
            return selectById(getConnection(), id);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public Account selectAccount(String email) {
        try {
            return selectByEmail(getConnection(), email);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean createAccount(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = getSelectStatementByEmail(connection, account.getEmail());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            updateAccountRow(resultSet, account);
            resultSet.insertRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = getSelectStatementById(connection, account.getId());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            updateAccountRow(resultSet, account);
            resultSet.updateRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean deleteAccount(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = getSelectStatementById(connection, account.getId());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            resultSet.deleteRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
