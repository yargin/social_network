package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity.AccountImpl;

import java.sql.Connection;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.dbutils.ConnectionFactory.getConnection;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public Account getAccount(int id) throws SQLException {
        try (Connection connection = getConnection()) {
            return new AccountImpl(id, connection);
        }
    }

    @Override
    public Account getAccount(String email) throws SQLException {
        try (Connection connection = getConnection()) {
            return new AccountImpl(email, connection);
        }
    }

    @Override
    public boolean insertAccount(Account account) throws SQLException {
        try (Connection connection = getConnection()) {
            return account.create(connection);
        }
    }

    @Override
    public boolean updateAccount(Account account) throws SQLException  {
        try (Connection connection = getConnection()) {
            return account.update(connection);
        }
    }

    @Override
    public boolean deleteAccount(Account account) throws SQLException  {
        try (Connection connection = getConnection()) {
            return account.delete(connection);
        }
    }
}
