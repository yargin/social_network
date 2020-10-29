package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.groupmembers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsMembers;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupsMembersDml extends ManyToManyDml<Account, Group> {
    private static final String SELECT_MEMBERS = buildQuery().selectJoin(AccountsTable.TABLE, GroupsMembers.TABLE,
            AccountsTable.ID, GroupsMembers.ACCOUNT_ID).where(GroupsMembers.GROUP_ID).build();
    private static final String SELECT_GROUPS = buildQuery().selectJoin(GroupsTable.TABLE, GroupsMembers.TABLE,
            GroupsTable.ID, GroupsMembers.GROUP_ID).join(AccountsTable.TABLE, GroupsTable.OWNER, AccountsTable.ID).
            where(GroupsMembers.ACCOUNT_ID).build();
    private static final String SELECT = buildQuery().select(GroupsMembers.TABLE).where(GroupsMembers.ACCOUNT_ID).
            and(GroupsMembers.GROUP_ID).build();

    @Override
    protected String getSecondSelectQuery() {
        return SELECT_GROUPS;
    }

    @Override
    protected Dml<Group> getSecondDml() {
        return new GroupDml();
    }

    @Override
    protected String getFirstSelectQuery() {
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
    public void updateRow(ResultSet resultSet, long firstId, long secondId) throws SQLException {
        resultSet.updateLong(GroupsMembers.ACCOUNT_ID, firstId);
        resultSet.updateLong(GroupsMembers.GROUP_ID, secondId);
    }
}
