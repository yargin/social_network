package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.friendship;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.friendship.FriendshipsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class FriendshipDml extends SelfManyToManyDml<Account> {
    private static final String ALIAS = "acc_id";
    private static final String SUB_SELECT = buildQuery().selectColumn(TABLE, FIRST_ACCOUNT, ALIAS).where(
            SECOND_ACCOUNT).union().selectColumn(TABLE, SECOND_ACCOUNT).where(FIRST_ACCOUNT).build();
    private static final String SELECT_BY_ID = buildQuery().joinSubSelect(AccountsTable.TABLE, SUB_SELECT,
            AccountsTable.ID, ALIAS).build();
    private static final String SELECT_BY_BOTH = buildQuery().select(TABLE).where(FIRST_ACCOUNT).and(SECOND_ACCOUNT).
            build();
    private static final AccountDml ACCOUNT_DML = new AccountDml();

    @Override
    public Collection<Account> select(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, id);
            return selectFromStatement(statement);
        }
    }

    public Collection<Account> selectFromStatement(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            Collection<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                Account account = ACCOUNT_DML.selectFromRow(resultSet);
                accounts.add(account);
            }
            return accounts;
        }
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, long firstId, long secondId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_BOTH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setLong(1, firstId);
        statement.setLong(2, secondId);
        return statement;
    }

    @Override
    public void updateRow(ResultSet resultSet, long firstId, long secondId) throws SQLException {
        resultSet.updateLong(1, firstId);
        resultSet.updateLong(2, secondId);
    }
}
