package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.FriendshipsRequestsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

@Component("friendshipsRequestsDml")
public class FriendshipsRequestsDml extends ManyToManyDml<Account, Account> {
    private static final String SELECT_REQUESTERS = buildQuery().selectJoin(AccountsTable.TABLE, TABLE, AccountsTable.ID,
            REQUESTER).where(RECEIVER).build();
    private static final String SELECT_RECEIVERS = buildQuery().selectJoin(AccountsTable.TABLE, TABLE, AccountsTable.ID,
            RECEIVER).where(REQUESTER).build();
    private static final String SELECT = buildQuery().selectAll(TABLE).where(REQUESTER).and(RECEIVER).build();
    private AccountDml accountDml;

    @Autowired
    public void setAccountDml(AccountDml accountDml) {
        this.accountDml = accountDml;
    }

    @Override
    protected String getSelectBySecondQuery() {
        return SELECT_RECEIVERS;
    }

    @Override
    protected Dml<Account> getSecondDml() {
        return accountDml;
    }

    @Override
    protected String getSelectByFirstQuery() {
        return SELECT_REQUESTERS;
    }

    @Override
    protected Dml<Account> getFirstDml() {
        return accountDml;
    }

    @Override
    protected String getSelectQuery() {
        return SELECT;
    }

    @Override
    public void updateRow(ResultSet resultSet, long requesterId, long receiverId) throws SQLException {
        resultSet.updateLong(REQUESTER, requesterId);
        resultSet.updateLong(RECEIVER, receiverId);
    }
}
