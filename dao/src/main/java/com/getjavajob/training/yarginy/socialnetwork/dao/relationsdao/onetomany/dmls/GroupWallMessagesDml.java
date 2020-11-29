package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages.GroupWallMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.GroupWallMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupWallMessagesDml extends OneToManyDml<Message> {
    private final MessagesTable table = new GroupWallMessagesTable();
    private final String selectByBoth = buildQuery().selectJoin(GroupsTable.TABLE, table.table, GroupsTable.ID,
            table.receiverId).where(GroupsTable.ID).and(table.id).build();
    private final String selectByGroupWall = buildQuery().selectJoin(AccountsTable.TABLE, table.table,
            AccountsTable.ID, table.author).where(table.receiverId).orderByDesc(table.posted).build();


    @Override
    protected String getSelectByBothQuery() {
        return selectByBoth;
    }

    @Override
    protected String getSelectByOneQuery() {
        return selectByGroupWall;
    }

    @Override
    protected Dml<Message> getManyDml() {
        return new GroupWallMessageDml();
    }
}
