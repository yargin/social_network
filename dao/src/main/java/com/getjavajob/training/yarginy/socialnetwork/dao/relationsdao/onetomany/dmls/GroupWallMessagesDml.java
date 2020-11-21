package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages.GroupWallMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.GroupWallMessagesTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupWallMessagesDml extends OneToManyDml<Group, Message> {
    private final MessagesTable table = new GroupWallMessagesTable();
    private final String SELECT_BY_BOTH = buildQuery().selectJoin(GroupsTable.TABLE, table.table, GroupsTable.ID,
            table.receiverId).where(GroupsTable.ID).and(table.id).build();
    private final String SELECT_BY_GROUP_WALL = buildQuery().selectJoin(AccountsTable.TABLE, table.table,
            AccountsTable.ID, table.author).where(table.receiverId).orderByDesc(table.posted).build();


    @Override
    protected String getSelectByBothQuery() {
        return SELECT_BY_BOTH;
    }

    @Override
    protected String getSelectByOneQuery() {
        return SELECT_BY_GROUP_WALL;
    }

    @Override
    protected Dml<Message> getManyDml() {
        return new GroupWallMessageDml();
    }
}
