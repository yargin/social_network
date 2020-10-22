package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.accountgroups;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountsGroupsDml extends OneToManyDml<Account, Group> {
    private static final String SELECT_BY_ACCOUNT = buildQuery().selectJoin(AccountsTable.TABLE, GroupsTable.TABLE,
            AccountsTable.ID, GroupsTable.OWNER).where(GroupsTable.OWNER).build();

    @Override
    protected String getSelectQuery() {
        return SELECT_BY_ACCOUNT;
    }

    @Override
    protected Dml<Group> getManyDml() {
        return new GroupDml();
    }
}
