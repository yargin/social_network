package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.groupmoderators;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsMembersTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsModeratorsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupModeratorsDml extends ManyToManyDml<Account, Group> {
    private static final String SELECT_MODERATORS = buildQuery().selectJoin(AccountsTable.TABLE,
            GroupsModeratorsTable.TABLE, AccountsTable.ID, GroupsModeratorsTable.ACCOUNT_ID).
            where(GroupsModeratorsTable.GROUP_ID).build();
    private static final String SELECT_GROUPS = buildQuery().selectJoin(GroupsTable.TABLE, GroupsModeratorsTable.TABLE,
            GroupsTable.ID, GroupsModeratorsTable.GROUP_ID).join(AccountsTable.TABLE, GroupsTable.OWNER,
            AccountsTable.ID).where(GroupsMembersTable.ACCOUNT_ID).build();
    private static final String SELECT = buildQuery().select(GroupsModeratorsTable.TABLE).where(
            GroupsModeratorsTable.ACCOUNT_ID).and(GroupsModeratorsTable.GROUP_ID).build();

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
        return SELECT_MODERATORS;
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
        resultSet.updateLong(GroupsMembersTable.ACCOUNT_ID, firstId);
        resultSet.updateLong(GroupsMembersTable.GROUP_ID, secondId);
    }
}
