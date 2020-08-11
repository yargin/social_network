package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.friendship;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.Accounts;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class FriendshipDml extends SelfManyToManyDml<Account> {
    private static final String ALIAS = "acc_id";
    private static final String SUB_SELECT = buildQuery().selectColumn(Friendships.TABLE, Friendships.FIRST_ACCOUNT, ALIAS).
            where(Friendships.SECOND_ACCOUNT).union().selectColumn(Friendships.TABLE, Friendships.SECOND_ACCOUNT).
            where(Friendships.FIRST_ACCOUNT).build();
    private static final String SELECT_BY_ID = buildQuery().joinSubSelect(Accounts.TABLE, SUB_SELECT, Accounts.ID, ALIAS).build();

    private static final String SELECT_BY_BOTH = buildQuery().select(Friendships.TABLE).where(Friendships.FIRST_ACCOUNT).
            and(Friendships.SECOND_ACCOUNT).build();
    private static final AccountDml ACCOUNT_DML = new AccountDml();

    @Override
    public Collection<Account> select(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
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
    public PreparedStatement getSelectStatement(Connection connection, int firstId, int secondId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_BOTH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, firstId);
        statement.setInt(2, secondId);
        return statement;
    }

    @Override
    public void updateRow(ResultSet resultSet, int firstId, int secondId) throws SQLException {
        resultSet.updateInt(1, firstId);
        resultSet.updateInt(2, secondId);
    }
}
