package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.accountsingroups;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.ManyToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountsInGroupsDml extends ManyToManyDml<Account, Group> {
    private static final String SELECT_MEMBERS = buildQuery().selectJoin(AccountsTable.TABLE, AccountsInGroups.TABLE,
            AccountsTable.ID, AccountsInGroups.ACCOUNT_ID).where(AccountsInGroups.GROUP_ID).build();
    private static final String SELECT_GROUPS = buildQuery().selectJoin(GroupsTable.TABLE, AccountsInGroups.TABLE,
            GroupsTable.ID, AccountsInGroups.GROUP_ID).join(AccountsTable.TABLE, GroupsTable.OWNER, AccountsTable.ID).
            where(AccountsInGroups.ACCOUNT_ID).build();
    private static final String SELECT = buildQuery().select(AccountsInGroups.TABLE).where(AccountsInGroups.ACCOUNT_ID).
            and(AccountsInGroups.GROUP_ID).build();

    @Override
    public Collection<Group> selectByFirst(Connection connection, long accountId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GROUPS)) {
            statement.setLong(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDml<Group> groupSql = new GroupDml();
                return groupSql.selectEntities(resultSet);
            }
        }
    }

    @Override
    public Collection<Account> selectBySecond(Connection connection, long groupId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_MEMBERS)) {
            statement.setLong(1, groupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDml<Account> accountDml = new AccountDml();
                return accountDml.selectEntities(resultSet);
            }
        }
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, long accountId, long groupId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setLong(1, accountId);
        statement.setLong(2, groupId);
        return statement;
    }

    @Override
    public void updateRow(ResultSet resultSet, long firstId, long secondId) throws SQLException {
        resultSet.updateLong(AccountsInGroups.ACCOUNT_ID, firstId);
        resultSet.updateLong(AccountsInGroups.GROUP_ID, secondId);
    }
}
