package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.accountsingroups;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.Accounts;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups.Groups;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.ManyToManyDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountsInGroupsDml extends ManyToManyDml<Account, Group> {
    private static final String SELECT_MEMBERS = buildQuery().selectJoin(Accounts.TABLE, AccountsInGroups.TABLE,
            Accounts.ID, AccountsInGroups.ACCOUNT_ID).where(AccountsInGroups.GROUP_ID).build();
    private static final String SELECT_GROUPS = buildQuery().selectJoin(Groups.TABLE, AccountsInGroups.TABLE,
            Groups.ID, AccountsInGroups.GROUP_ID).where(AccountsInGroups.ACCOUNT_ID).build();
    private static final String SELECT = buildQuery().select(AccountsInGroups.TABLE).where(AccountsInGroups.ACCOUNT_ID).
            and(AccountsInGroups.GROUP_ID).build();

    @Override
    public Collection<Group> selectByFirst(Connection connection, int accountId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GROUPS)) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDml<Group> groupSql = new GroupDml();
                return groupSql.selectEntities(resultSet);
            }
        }
    }

    @Override
    public Collection<Account> selectBySecond(Connection connection, int groupId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_MEMBERS)) {
            statement.setInt(1, groupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDml<Account> accountDml = new AccountDml();
                return accountDml.selectEntities(resultSet);
            }
        }
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, int accountId, int groupId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, accountId);
        statement.setInt(2, groupId);
        return statement;
    }

    @Override
    public void updateRow(ResultSet resultSet, int firstId, int secondId) throws SQLException {
        resultSet.updateInt(AccountsInGroups.ACCOUNT_ID, firstId);
        resultSet.updateInt(AccountsInGroups.GROUP_ID, secondId);
    }
}
