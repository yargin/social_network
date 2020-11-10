package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

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
    private static final String SELECT_UPDATE_BY_NAME = buildQuery().select(TABLE).where(NAME).build();
    private static final String SELECT_UPDATE_BY_ID = buildQuery().select(TABLE).where(ID).build();
    private final AccountDml accountDml = new AccountDml();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();

    @Override
    protected String getSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectByAltKey() {
        return SELECT_BY_NAME;
    }

    @Override
    protected String getSelectForUpdateByAltKey() {
        return SELECT_UPDATE_BY_NAME;
    }

    @Override
    protected String getSelectForUpdateById() {
        return SELECT_UPDATE_BY_ID;
    }

    @Override
    protected void setAltKeyParams(PreparedStatement statement, Group group) throws SQLException {
        statement.setString(1, group.getName());
    }

    @Override
    public Group selectViewFromRow(ResultSet resultSet) throws SQLException {
        Group group = new GroupImpl();
        group.setId(resultSet.getLong(ID));
        group.setName(resultSet.getString(NAME));
//        group.setPhotoPreview(resultSet.getBytes(PHOTO_PREVIEW));
//        group.setPhoto(resultSet.getBytes(PHOTO));
        return group;
    }

    @Override
    public Group selectFromRow(ResultSet resultSet) throws SQLException {
        Group group = selectViewFromRow(resultSet);
        group.setDescription(resultSet.getString(DESCRIPTION));
        group.setCreationDate(resultSet.getDate(CREATION_DATE));
        group.setPhoto(resultSet.getBytes(PHOTO));
        Account owner = accountDml.selectViewFromRow(resultSet);
        group.setOwner(owner);
        return group;
    }

    @Override
    public void updateRow(ResultSet resultSet, Group group, Group storedGroup) throws SQLException {
        updateFieldIfDiffers(group::getName, storedGroup::getName, resultSet::updateString, NAME);
        updateFieldIfDiffers(group::getDescription, storedGroup::getDescription, resultSet::updateString, DESCRIPTION);
        updateFieldIfDiffers(group::getCreationDate, storedGroup::getCreationDate, resultSet::updateDate, CREATION_DATE);
        updateFieldIfDiffers(group::getPhoto, storedGroup::getPhoto, resultSet::updateBytes, PHOTO);
//        updateFieldIfDiffers(group::getPhotoPreview, storedGroup::getPhotoPreview, resultSet::updateBytes, PHOTO_PREVIEW);
        if (isNull(group.getOwner())) {
            return;
        }
        Account owner = accountDao.approveFromStorage(group.getOwner());
        group.setOwner(owner);
        updateFieldIfDiffers(group::getOwner, storedGroup::getOwner, resultSet::updateLong, OWNER, Account::getId);
    }

    @Override
    public Collection<Group> selectEntities(ResultSet resultSet) throws SQLException {
        Collection<Group> communities = new ArrayList<>();
        while (resultSet.next()) {
            Group group = selectViewFromRow(resultSet);
            communities.add(group);
        }
        return communities;
    }

    @Override
    public Group getNullEntity() {
        return getNullGroup();
    }
}
