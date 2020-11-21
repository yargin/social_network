package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages.AccountWallMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.AccountWallMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountWallMessagesDml extends OneToManyDml<Account, Message> {
    private final MessagesTable table = new AccountWallMessagesTable();
    private final String SELECT_BY_BOTH = buildQuery().selectJoin(AccountsTable.TABLE, table.table, AccountsTable.ID,
            table.receiverId).where(AccountsTable.ID).and(table.id).build();
    private final String SELECT_BY_ACCOUNT_WALL = buildQuery().selectJoin(AccountsTable.TABLE, table.table,
            AccountsTable.ID, table.author).where(table.receiverId).orderByDesc(table.posted).build();


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
