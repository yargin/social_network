package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {
    private final DbConnector dbConnector;

    public AccountDAOImpl(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public Account selectAccount(int id) {
        try {
            return AccountSQLQueriesHandler.selectById(dbConnector.getConnection(), id);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public Account selectAccount(String email) {
        try {
            return AccountSQLQueriesHandler.selectByEmail(dbConnector.getConnection(), email);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean createAccount(Account account) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = AccountSQLQueriesHandler.getSelectStatementByEmail(connection, account.getEmail());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            AccountSQLQueriesHandler.updateAccountRow(resultSet, account);
            resultSet.insertRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = AccountSQLQueriesHandler.getSelectStatementByEmail(connection, account.getEmail());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            AccountSQLQueriesHandler.updateAccountRow(resultSet, account);
            resultSet.updateRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean deleteAccount(Account account) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = AccountSQLQueriesHandler.getSelectStatementByEmail(connection, account.getEmail());
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
