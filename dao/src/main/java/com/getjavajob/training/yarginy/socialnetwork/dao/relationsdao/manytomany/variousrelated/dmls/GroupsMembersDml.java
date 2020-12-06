package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsMembersTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupsMembersDml extends ManyToManyDml<Account, Group> {
    private static final String SELECT_MEMBERS = buildQuery().selectJoin(AccountsTable.TABLE, GroupsMembersTable.TABLE,
            AccountsTable.ID, GroupsMembersTable.ACCOUNT_ID).where(GroupsMembersTable.GROUP_ID).build();
    private static final String SELECT_GROUPS = buildQuery().selectJoin(GroupsTable.TABLE, GroupsMembersTable.TABLE,
            GroupsTable.ID, GroupsMembersTable.GROUP_ID).join(AccountsTable.TABLE, GroupsTable.OWNER, AccountsTable.ID).
            where(GroupsMembersTable.ACCOUNT_ID).build();
    private static final String SELECT = buildQuery().selectAll(GroupsMembersTable.TABLE).where(GroupsMembersTable.ACCOUNT_ID).
            and(GroupsMembersTable.GROUP_ID).build();

    @Override
    protected String getSelectBySecondQuery() {
        return SELECT_GROUPS;
    }

    @Override
    protected Dml<Group> getSecondDml() {
        return new GroupDml();
    }

    @Override
    protected String getSelectByFirstQuery() {
        return SELECT_MEMBERS;
    }

    @Override
    protected Dml<Account> getFirstDml() {
        return new AccountDml();
    }

    @Override
    protected String getSelectQuery() {
        return SELECT;
    }

    @Override
    public void updateRow(ResultSet resultSet, long accountId, long groupId) throws SQLException {
        resultSet.updateLong(GroupsMembersTable.ACCOUNT_ID, accountId);
        resultSet.updateLong(GroupsMembersTable.GROUP_ID, groupId);
    }
}
