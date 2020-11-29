package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.FriendshipsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class FriendshipDml extends SelfManyToManyDml<Account> {
    private static final String ALIAS = "acc_id";
    private static final String SUB_SELECT = buildQuery().selectColumn(TABLE, FIRST_ACCOUNT, ALIAS).where(
            SECOND_ACCOUNT).union().selectColumn(TABLE, SECOND_ACCOUNT).where(FIRST_ACCOUNT).build();
    private static final String SELECT_BY_ID = buildQuery().joinSubSelect(AccountsTable.TABLE, SUB_SELECT,
            AccountsTable.ID, ALIAS).build();
    private static final String SELECT_BY_BOTH = "SELECT * FROM " + TABLE + " WHERE (" + FIRST_ACCOUNT + " = ? AND " +
            SECOND_ACCOUNT + " = ?) OR (" + SECOND_ACCOUNT + " = ? AND " + FIRST_ACCOUNT + " = ?);";

    @Override
    public Collection<Account> retrieve(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            statement.setLong(1, id);
            statement.setLong(2, id);
            return selectFromStatement(statement);
        }
    }

    @Override
    protected Dml<Account> getEntityDml() {
        return new AccountDml();
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, long firstId, long secondId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_BOTH, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setLong(1, firstId);
        statement.setLong(2, secondId);
        statement.setLong(3, firstId);
        statement.setLong(4, secondId);
        return statement;
    }

    @Override
    public void updateRow(ResultSet resultSet, long firstId, long secondId) throws SQLException {
        resultSet.updateLong(1, firstId);
        resultSet.updateLong(2, secondId);
    }
}
