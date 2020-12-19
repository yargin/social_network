package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.InsertQueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.QueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.UpdateQueryAndParamPlacer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.GroupsTable.*;

@Repository("groupDaoFieldsHandler")
public class GroupDaoFieldsHandler implements DaoFieldsHandler<Group> {
    private static final String SELECT_BY_ID = "SELECT * FROM Groups WHERE id = :id";
    private static final String SELECT_BY_ALT_KEY = "SELECT * FROM Groups WHERE name = :name";
    private static final String SELECT_ALL = "SELECT * FROM Groups";
    private static final String DELETE_GROUP = "DELETE FROM Groups WHERE name = :name";
    private final AccountDaoFieldsHandler accountDaoFieldsHandler;

    @Autowired
    public GroupDaoFieldsHandler(AccountDaoFieldsHandler accountDaoFieldsHandler) {
        this.accountDaoFieldsHandler = accountDaoFieldsHandler;
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
        Account owner = accountDaoFieldsHandler.mapViewRow(resultSet, i);
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
        return null;
    }

    @Override
    public Group getNullEntity() {
        return null;
    }

    static class GroupInitializer extends Initializer<Group> {
        @Override
        public void initUpdateParams(Group entity, Group storedEntity) {

        }

        @Override
        public void initInsertParams(Group entity) {

        }

        @Override
        public void setPKey(Group storedEntity) {

        }
    }
}
