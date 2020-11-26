package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public abstract class OneToManyDml<O extends Entity, M extends Entity> {
    public Collection<M> retrieveManyByOne(Connection connection, long oneId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(getSelectByOneQuery(), ResultSet.
                TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setLong(1, oneId);
            try {
                statement.setLong(2, oneId);
            } catch (SQLException ignore) {
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                return getManyDml().retrieveEntities(resultSet);
            }
        }
    }

    public boolean retrieveByBoth(Connection connection, long oneId, long manyId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(getSelectByBothQuery(), ResultSet.
                TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setLong(1, oneId);
            statement.setLong(2, manyId);
            try {
                statement.setLong(3, manyId);
            } catch (SQLException ignore) {
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    protected abstract String getSelectByBothQuery();

    protected abstract String getSelectByOneQuery();

    protected abstract Dml<M> getManyDml();
}
