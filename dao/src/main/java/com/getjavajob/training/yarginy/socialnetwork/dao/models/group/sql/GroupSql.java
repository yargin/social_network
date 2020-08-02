package com.getjavajob.training.yarginy.socialnetwork.dao.models.group.sql;

import com.getjavajob.training.yarginy.socialnetwork.dao.models.EntitySql;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.sql.AccountSql;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.sql.Accounts;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.accountsingroups.AccountsInGroups;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

public class GroupSql implements EntitySql<Group> {
    private static final String SELECT_BY_ID = "SELECT * FROM " + Groups.TABLE + " WHERE " + Groups.ID + " = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM " + Groups.TABLE + " WHERE " + Groups.NAME + " = ?";

    private static final String SELECT_MEMBERS = "SELECT * FROM " + Accounts.TABLE + " JOIN " + AccountsInGroups.TABLE +
            AccountsInGroups.TABLE + " ON " + Accounts.TABLE + "." + Accounts.ID + " = "+ AccountsInGroups.TABLE + "." +
            AccountsInGroups.ACCOUNT_ID + " WHERE " + AccountsInGroups.TABLE + "." + AccountsInGroups.GROUP_ID + " = ?";

    @Override
    public PreparedStatement getSelectStatement(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, String identifier) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, identifier);
        return statement;
    }

    @Override
    public Group selectFromRow(ResultSet resultSet) throws SQLException {
        Group group = new GroupImpl();
        group.setId(resultSet.getInt(Groups.ID));
        group.setName(resultSet.getString(Groups.NAME));
        group.setDescription(resultSet.getString(Groups.DESCRIPTION));
        return group;
    }

    @Override
    public void updateRow(ResultSet resultSet, Group group) throws SQLException {
        resultSet.updateString(Groups.NAME, group.getName());
        resultSet.updateString(Groups.DESCRIPTION, group.getDescription());
        if (!isNull(group.getOwner())) {
            resultSet.updateInt(Groups.OWNER, group.getOwner().getId());
        }
    }

    @Override
    public Collection<Group> selectEntities(ResultSet resultSet) throws SQLException {
        Collection<Group> groups = new ArrayList<>();
        while (resultSet.next()) {
            Group group = selectFromRow(resultSet);
            groups.add(group);
        }
        return groups;
    }

    public Collection<Account> selectMembers(Connection connection, Group group) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_MEMBERS)) {
            statement.setInt(1, group.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                EntitySql<Account> accountSql = new AccountSql();
                return accountSql.selectEntities(resultSet);
            }
        }
    }
}
