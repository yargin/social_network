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
        try (PreparedStatement statement = connection.prepareStatement(getSelectQuery())) {
            statement.setLong(1, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                return getManyDml().selectEntities(resultSet);
            }
        }
    }

    public boolean updateMany(Connection connection, Collection<M> many, O entity) throws SQLException {
        Collection<M> storedMany = selectByOne(connection, entity);
        try(PreparedStatement statement = connection.prepareStatement(getSelectQuery(), ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, entity.getId());
            try(ResultSet resultSet = statement.executeQuery()) {
                //delete that not in many but in stored
                //need id's to delete
                storedMany.retainAll(many);
                many.retainAll(storedMany);

                //add that in many but not in stored
            }
        }
        return false;
    }

    protected abstract String getSelectQuery();

    protected abstract Dml<M> getManyDml();
}
