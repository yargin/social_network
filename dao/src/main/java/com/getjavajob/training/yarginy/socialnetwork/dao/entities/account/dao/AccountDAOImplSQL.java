package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.factories.AccountDTOFactorySQL;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.factories.AccountFactorySQL;

import java.sql.Connection;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.sql.ConnectionFactory.getConnection;

public class AccountDAOImplSQL implements AccountDAO {
    AccountFactorySQL factory = new AccountDTOFactorySQL();

    @Override
    public Account retrieveAccount(int id) {
        try (Connection connection = getConnection()) {
            return factory.getInstance(id, connection);
        }catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public Account retrieveAccount(String email) {
        try (Connection connection = getConnection()) {
            return factory.getInstance(email, connection);
        }catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public boolean createAccount(Account account) {
        try (Connection connection = getConnection()) {
            return account.create(connection);
        }catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection()) {
            return account.update(connection);
        }catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    @Override
    public boolean deleteAccount(Account account) {
        try (Connection connection = getConnection()) {
            return account.delete(connection);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }
}
