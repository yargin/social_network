package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql.AccountQueryHandler.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql.ConnectionFactory.getConnection;

public class AccountDAOImpl implements AccountDAO {
    public AccountDTO selectAccount(int id) {
        try {
            return selectById(getConnection(), id);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    public AccountDTO selectAccount(String email) {
        try {
            return selectByEmail(getConnection(), email);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public boolean createAccount(AccountDTO account) {
        try {
            Connection connection = getConnection();
            ResultSet resultSet = getResultSetByEmail(connection, account.getEmail());
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            buildInsert(resultSet, account);
            resultSet.insertRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public boolean updateAccount(AccountDTO account) {
        try {
            Connection connection = getConnection();
            ResultSet resultSet = getResultSetById(connection, account.getId());
            if (!resultSet.next()) {
                return false;
            }
            buildInsert(resultSet, account);
            resultSet.updateRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public boolean deleteAccount(AccountDTO account) {
        try {
            Connection connection = getConnection();
            ResultSet resultSet = getResultSetById(connection, account.getId());
            if (!resultSet.next()) {
                return false;
            }
            resultSet.deleteRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }
}
