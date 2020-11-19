package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountWallMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountWallMessagesTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountWallMessagesDml extends OneToManyDml<Account, Message> {
    private static final String SELECT_BY_BOTH = buildQuery().selectJoin(AccountsTable.TABLE, TABLE, AccountsTable.ID,
            ACCOUNT_WALL_ID).where(AccountsTable.ID).and(ID).build();
    private static final String SELECT_BY_ACCOUNT_WALL = buildQuery().selectJoin(AccountsTable.TABLE, TABLE,
            AccountsTable.ID, AUTHOR).where(ACCOUNT_WALL_ID).orderByDesc(POSTED).build();


    @Override
    protected String getSelectByBothQuery() {
        return SELECT_BY_BOTH;
    }

    @Override
    protected String getSelectByOneQuery() {
        return SELECT_BY_ACCOUNT_WALL;
    }

    @Override
    protected Dml<Message> getManyDml() {
        return new AccountWallMessageDml();
    }
}
