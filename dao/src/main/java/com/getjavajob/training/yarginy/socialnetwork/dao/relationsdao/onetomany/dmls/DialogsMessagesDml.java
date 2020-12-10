package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages.DialogMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.DialogsMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;
import org.springframework.stereotype.Component;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

@Component("dialogsMessagesDml")
public class DialogsMessagesDml extends OneToManyDml<Message> {
    private final MessagesTable table = new DialogsMessagesTable();
    private final String selectByBoth = buildQuery().selectJoin(AccountsTable.TABLE, table.table, AccountsTable.ID,
            table.receiverId).where(AccountsTable.ID).and(table.id).build();
    private final String selectByDialog = buildQuery().selectJoin(AccountsTable.TABLE, table.table,
            AccountsTable.ID, table.author).where(table.receiverId).orderByDesc(table.posted).build();

    @Override
    protected String getSelectByBothQuery() {
        return selectByBoth;
    }

    @Override
    protected String getSelectByOneQuery() {
        return selectByDialog;
    }

    @Override
    protected Dml<Message> getManyDml() {
        return new DialogMessageDml();
    }
}
