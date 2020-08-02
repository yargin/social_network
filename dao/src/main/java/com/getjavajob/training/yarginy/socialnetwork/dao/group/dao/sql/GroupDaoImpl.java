package com.getjavajob.training.yarginy.socialnetwork.dao.group.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.GroupDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.getjavajob.training.yarginy.socialnetwork.dao.group.dao.sql.GroupSQLQueriesHandler.*;

public class GroupDaoImpl implements GroupDao {
    private final DbConnector dbConnector;

    public GroupDaoImpl(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public Group selectGroup(int id) {
        try {
            return selectById(dbConnector.getConnection(), id);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Group selectGroup(String name) {
        try {
            return selectByName(dbConnector.getConnection(), name);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean createGroup(Group group) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = getSelectStatementByName(connection, group.getName());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            updateGroupRow(resultSet, group);
            resultSet.insertRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean updateGroup(Group group) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = getSelectStatementByName(connection, group.getName());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            updateGroupRow(resultSet, group);
            resultSet.updateRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean deleteGroup(Group group) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = getSelectStatementByName(connection, group.getName());
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
    public List<Account> selectMembers(Group group) {
        return null;
    }
}
