package com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.GroupImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.AbstractDml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.entities.NullEntitiesFactory.getNullGroup;
import static com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups.GroupsTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class GroupDml extends AbstractDml<Group> {
    private static final String SELECT_ALL = buildQuery().select(TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().select(TABLE).where(ID).build();
    private static final String SELECT_BY_NAME = buildQuery().select(TABLE).where(NAME).build();

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setLong(1, id);
        return statement;
    }

    @Override
    public PreparedStatement getSelectStatement(Connection connection, String identifier) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, identifier);
        return statement;
    }

    @Override
    public Group selectFromRow(ResultSet resultSet) throws SQLException {
        Group group = new GroupImpl();
        group.setId(resultSet.getLong(ID));
        group.setName(resultSet.getString(NAME));
        group.setDescription(resultSet.getString(DESCRIPTION));
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
