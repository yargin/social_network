package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class SelfManyToManyDml<E extends Entity> implements Serializable {
    public abstract Collection<E> retrieve(Connection connection, long id) throws SQLException;

    public abstract PreparedStatement getSelectStatement(Connection connection, long firstId, long secondId) throws
            SQLException;

    public abstract void updateRow(ResultSet resultSet, long firstId, long secondId) throws SQLException;

    public Collection<E> selectFromStatement(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            Dml<E> entityDml = getEntityDml();
            Collection<E> entities = new ArrayList<>();
            while (resultSet.next()) {
                E entity = entityDml.retrieveViewFromRow(resultSet);
                entities.add(entity);
            }
            return entities;
        }
    }

    protected abstract Dml<E> getEntityDml();
}
