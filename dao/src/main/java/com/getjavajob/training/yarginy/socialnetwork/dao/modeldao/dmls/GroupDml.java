package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullGroup;
import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;
import static java.util.Objects.isNull;

public class GroupDml extends AbstractDml<Group> {
    private static final String SELECT_ALL = buildQuery().select(TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(ID).build();
    private static final String SELECT_BY_NAME = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, OWNER,
            AccountsTable.ID).where(NAME).build();
    private static final String SELECT_UPDATE = buildQuery().select(TABLE).where(NAME).build();
    private final AccountDml accountDml = new AccountDml();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();

    @Override
    public PreparedStatement getSelect(Connection connection, Group group) throws SQLException {
        PreparedStatement statement;
        if (isNull(group)) {
            statement = connection.prepareStatement(SELECT_ALL);
        } else if (group.getId() > 0) {
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, group.getId());
        } else {
            statement = connection.prepareStatement(SELECT_BY_NAME);
            statement.setString(1, group.getName());
        }
        return statement;
    }

    @Override
    public PreparedStatement getUpdatableSelect(Connection connection, Group group) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, group.getName());
        return statement;
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
    public void updateRow(ResultSet resultSet, Group group, Group storedGroup) throws SQLException {
        updateFieldIfDiffers(group::getName, storedGroup::getName, resultSet::updateString, NAME);
        updateFieldIfDiffers(group::getDescription, storedGroup::getDescription, resultSet::updateString, DESCRIPTION);
        Account owner = accountDao.approveFromStorage(group.getOwner());
        group.setOwner(owner);
        updateFieldIfDiffers(group::getOwner, storedGroup::getOwner, resultSet::updateLong, OWNER, Account::getId);
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
