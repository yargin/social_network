package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountGroupsDml extends OneToManyDml<Group> {
    private static final String SELECT_BY_ACCOUNT = buildQuery().selectJoin(AccountsTable.TABLE, GroupsTable.TABLE,
            AccountsTable.ID, GroupsTable.OWNER).where(GroupsTable.OWNER).build();
    private static final String SELECT_BY_BOTH = buildQuery().selectJoin(AccountsTable.TABLE, GroupsTable.TABLE,
            AccountsTable.ID, GroupsTable.OWNER).where(GroupsTable.OWNER).and(GroupsTable.ID).build();

    @Override
    protected String getSelectByOneQuery() {
        return SELECT_BY_ACCOUNT;
    }

    @Override
    protected Dml<Group> getManyDml() {
        return new GroupDml();
    }

    @Override
    protected String getSelectByBothQuery() {
        return SELECT_BY_BOTH;
    }
}
