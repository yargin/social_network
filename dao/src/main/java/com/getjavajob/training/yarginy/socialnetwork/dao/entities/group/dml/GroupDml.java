package com.getjavajob.training.yarginy.socialnetwork.dao.entities.group.dml;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.EntityDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dml.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dml.Accounts;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.identifying.accountsingroups.sql.AccountsInGroups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class GroupDml extends EntityDml<Group> {
    private static final String SELECT_ALL = buildQuery().select(Groups.TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().select(Groups.TABLE).where(Groups.ID).build();
    private static final String SELECT_BY_NAME = buildQuery().select(Groups.TABLE).where(Groups.NAME).build();
    private static final String SELECT_MEMBERS = buildQuery().selectJoin(Accounts.TABLE, AccountsInGroups.TABLE,
            Accounts.ID, AccountsInGroups.ACCOUNT_ID).where(AccountsInGroups.GROUP_ID).build();

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

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
        Collection<Group> communities = new ArrayList<>();
        while (resultSet.next()) {
            Group group = selectFromRow(resultSet);
            communities.add(group);
        }
        return communities;
    }

    public Collection<Account> selectMembers(Connection connection, Group group) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_MEMBERS)) {
            statement.setInt(1, group.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                EntityDml<Account> accountDml = new AccountDml();
                return accountDml.selectEntities(resultSet);
            }
        }
    }
}
