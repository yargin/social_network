package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.batchdaos.handlers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.batchdaos.BatchDaoFieldsHandler;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos.handlers.AccountDaoHandler;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.InsertQueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.QueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.UpdateQueryAndParamPlacer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable.*;
import static java.util.Objects.isNull;

@Repository("groupDaoFieldsHandler")
public class GroupDaoFieldsHandler implements BatchDaoFieldsHandler<Group> {
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE + " WHERE id = :id";
    private static final String SELECT_BY_ALT_KEY = "SELECT * FROM " + TABLE + " WHERE name = :name";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE;
    private static final String DELETE_GROUP = "DELETE FROM " + TABLE + " WHERE name = :name";
    private final AccountDaoHandler accountDaoHandler;

    @Autowired
    public GroupDaoFieldsHandler(AccountDaoHandler accountDaoHandler) {
        this.accountDaoHandler = accountDaoHandler;
    }

    @Override
    public String getSelectByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    public String getSelectByAltKeyQuery() {
        return SELECT_BY_ALT_KEY;
    }

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_GROUP;
    }

    @Override
    public QueryAndParamPlacer getInsertQueryAndParameters(Group group) {
        return new InsertQueryAndParamPlacer<>(TABLE, new GroupInitializer(), group);
    }

    @Override
    public QueryAndParamPlacer getUpdateQueryAndParameters(Group group, Group storedGroup) {
        return new UpdateQueryAndParamPlacer<>(TABLE, new GroupInitializer(), group, storedGroup, new String[]{ID});
    }

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = mapViewRow(resultSet, i);
        group.setDescription(resultSet.getString(DESCRIPTION));
        group.setCreationDate(resultSet.getDate(CREATION_DATE));
        group.setPhoto(resultSet.getBytes(PHOTO));
        Account owner = accountDaoHandler.mapViewRow(resultSet, i);
        group.setOwner(owner);
        return group;
    }

    @Override
    public Group mapViewRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new GroupImpl();
        group.setId(resultSet.getLong(ID));
        group.setName(resultSet.getString(NAME));
        return group;
    }

    @Override
    public MapSqlParameterSource getAltKeyParameter(Group group) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(NAME, group.getName(), Types.VARCHAR);
        return parameters;
    }

    public MapSqlParameterSource getPKeyParameter(Group group) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(ID, group.getId(), Types.BIGINT);
        return parameters;
    }

    @Override
    public MapSqlParameterSource[] getAltKeyParameter(Collection<Group> groups) {
        int size = groups.size();
        MapSqlParameterSource[] parameters = new MapSqlParameterSource[size];
        int i = 0;
        for (Group group : groups) {
            parameters[i] = new MapSqlParameterSource().addValue(NAME, group.getName(), Types.VARCHAR);
        }
        return parameters;
    }

    @Override
    public QueryAndParamPlacer getInsertQueryAndParameters(Collection<Group> groups) {
        return null;
    }

    @Override
    public Group getNullEntity() {
        return null;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    static class GroupInitializer extends Initializer<Group> {
        @Override
        public void initUpdateParams(Group group, Group storedGroup) {
            placer.placeValueIfDiffers(group::getName, storedGroup::getName, NAME, Types.VARCHAR);
            placer.placeValueIfDiffers(group::getDescription, storedGroup::getDescription, DESCRIPTION, Types.VARCHAR);
            placer.placeValueIfDiffers(group::getCreationDate, storedGroup::getCreationDate, CREATION_DATE,
                    Types.DATE);
            placer.placeValueIfDiffers(group::getPhoto, storedGroup::getPhoto, PHOTO, Types.BLOB);
            if (isNull(group.getOwner())) {
                return;
            }
            placer.placeValueIfDiffers(group::getOwner, storedGroup::getOwner, OWNER, Types.BIGINT, Account::getId);
        }

        @Override
        public void initInsertParams(Group group) {
            placer.placeValue(group::getName, NAME, Types.VARCHAR);
            placer.placeValue(group::getDescription, DESCRIPTION, Types.VARCHAR);
            placer.placeValue(group::getCreationDate, CREATION_DATE, Types.DATE);
            placer.placeValue(group::getPhoto, PHOTO, Types.BLOB);
            placer.placeValue(group::getOwner, OWNER, Types.BIGINT);
        }

        @Override
        public void setPKey(Group storedGroup) {
            placer.placeKey(storedGroup::getId, ID, Types.BIGINT);
        }
    }
}
