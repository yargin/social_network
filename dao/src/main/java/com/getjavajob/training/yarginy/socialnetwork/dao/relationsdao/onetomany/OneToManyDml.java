package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public abstract class OneToManyDml<O extends Entity, M extends Entity> {
    public Collection<M> selectByOne(Connection connection, O entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(getSelectQuery(), ResultSet.
                TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setLong(1, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                return getManyDml().selectEntities(resultSet);
            }
        }
    }

    protected abstract String getSelectQuery();

    protected abstract Dml<M> getManyDml();
}
