package com.getjavajob.training.yarginy.socialnetwork.dao.group.dao.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.AccountSQLQueriesHandler;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.dtoimplementation.GroupImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.group.Group.*;
import static java.util.Objects.isNull;

public class GroupSQLQueriesHandler {
    private static final String TABLE = "Groups";
    private static final String ID = "ID";
    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final String OWNER = "Owner";
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + NAME + " = ?";

    public static void updateGroupRow(ResultSet resultSet, Group group) throws SQLException {
        resultSet.updateString(NAME, group.getName());
        resultSet.updateString(DESCRIPTION, group.getDescription());
        if (!isNull(group.getOwner())) {
            resultSet.updateInt(OWNER, group.getOwner().getId());
        }
    }

    public static PreparedStatement getSelectStatementByName(Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, name);
        return statement;
    }

    public static PreparedStatement getSelectStatementById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);
        return statement;
    }

    public static Group selectById(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            return selectGroupFromStatement(connection, statement);
        }
    }

    public static Group selectByName(Connection connection, String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            return selectGroupFromStatement(connection, statement);
        }
    }

    private static Group selectGroupFromStatement(Connection connection, PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return getNullGroup();
            }
            Group group = selectGroupRow(resultSet);
            Account owner = AccountSQLQueriesHandler.selectById(connection, resultSet.getInt(OWNER));
            group.setOwner(owner);
            return group;
        }
    }

    public static Group selectGroupRow(ResultSet resultSet) throws SQLException {
        Group group = new GroupImpl();
        group.setId(resultSet.getInt(ID));
        group.setName(resultSet.getString(NAME));
        group.setDescription(resultSet.getString(DESCRIPTION));
        return group;
    }

    public static Group selectGroupsFromOwner(ResultSet resultSet, Account account) throws SQLException {
        if (!resultSet.next()) {
            return getNullGroup();
        }
        Group group = selectGroupRow(resultSet);
        group.setOwner(account);
        return group;
    }
}
