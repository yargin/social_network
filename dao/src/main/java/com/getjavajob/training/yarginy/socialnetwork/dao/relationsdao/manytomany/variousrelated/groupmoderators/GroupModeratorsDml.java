package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.groupmoderators;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupModerators;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsMembers;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupModeratorsDml extends ManyToManyDml<Account, Group> {
    private static final String SELECT_MEMBERS = buildQuery().selectJoin(AccountsTable.TABLE, GroupModerators.TABLE,
            AccountsTable.ID, GroupModerators.ACCOUNT_ID).where(GroupModerators.GROUP_ID).build();
    private static final String SELECT_GROUPS = buildQuery().selectJoin(GroupsTable.TABLE, GroupModerators.TABLE,
            GroupsTable.ID, GroupModerators.GROUP_ID).join(AccountsTable.TABLE, GroupsTable.OWNER, AccountsTable.ID).
            where(GroupsMembers.ACCOUNT_ID).build();
    private static final String SELECT = buildQuery().select(GroupModerators.TABLE).where(GroupModerators.ACCOUNT_ID).
            and(GroupModerators.GROUP_ID).build();

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
