package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.dao.sql.GroupSQLQueriesHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.AccountSQLQueriesHandler.*;
import static java.util.Collections.emptyList;

public class AccountDaoImpl implements AccountDao {
    private final DbConnector dbConnector;

    public AccountDaoImpl(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public Account selectAccount(int id) {
        try {
            return selectById(dbConnector.getConnection(), id);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Account selectAccount(String email) {
        try {
            return selectByEmail(dbConnector.getConnection(), email);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean createAccount(Account account) {
        try (Connection connection = dbConnector.getConnection();
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
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = getSelectStatementByEmail(connection, account.getEmail());
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
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = getSelectStatementByEmail(connection, account.getEmail());
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

    @Override
    public List<Group> selectOwnershipGroups(Account account) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = GroupSQLQueriesHandler.getSelectStatementById(connection, account.getId());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return emptyList();
            }
            List<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                Group group = GroupSQLQueriesHandler.selectGroupsFromOwner(resultSet, account);
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<Group> selectMembershipGroups(Account account) {
        try (Connection connection = dbConnector.getConnection()) {
            return selectGroupsByAccount(connection, account);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
