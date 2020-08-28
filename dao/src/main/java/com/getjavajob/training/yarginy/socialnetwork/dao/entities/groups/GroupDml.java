package com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountsTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.entities.NullEntitiesFactory.getNullGroup;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups.GroupsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupDml extends AbstractDml<Group> {
    private static final String SELECT_ALL = buildQuery().select(TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(ID).build();
    private static final String SELECT_BY_NAME = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(NAME).build();
    private static final String SELECT_UPDATE = buildQuery().select(TABLE).where(NAME).build();
    private final AccountDml accountDml = new AccountDml();

    @Override
    protected String getSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectByIdentifier() {
        return SELECT_BY_NAME;
    }

    @Override
    protected String getUpdatableSelect() {
        return SELECT_UPDATE;
    }

    @Override
    protected String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    public Group selectFromRow(ResultSet resultSet) throws SQLException {
        Group group = new GroupImpl();
        group.setId(resultSet.getLong(ID));
        group.setName(resultSet.getString(NAME));
        group.setDescription(resultSet.getString(DESCRIPTION));
        Account owner = accountDml.selectFromRow(resultSet);
        group.setOwner(owner);
        return group;
    }

    @Override
    public void updateRow(ResultSet resultSet, Group group) throws SQLException {
        resultSet.updateString(NAME, group.getName());
        resultSet.updateString(DESCRIPTION, group.getDescription());
        resultSet.updateLong(OWNER, group.getOwner().getId());
    }

    @Override
    public Collection<Group> selectEntities(ResultSet resultSet) throws SQLException {
        Collection<Group> communities = new ArrayList<>();
        while (resultSet.next()) {
            Group group = selectFromRow(resultSet);
            communities.add(group);
        }
        return communities;
    }

    @Override
    public Group getNullEntity() {
        return getNullGroup();
    }
}
