package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dml;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public abstract class ManyToManyDml<F extends Entity, S extends Entity> implements Serializable {
    public Collection<S> retrieveByFirst(Connection connection, long firstId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(getSelectBySecondQuery())) {
            statement.setLong(1, firstId);
            try (ResultSet resultSet = statement.executeQuery()) {
                Dml<S> secondDml = getSecondDml();
                return secondDml.retrieveEntities(resultSet);
            }
        }
    }

    protected abstract String getSelectBySecondQuery();

    protected abstract Dml<S> getSecondDml();

    public Collection<F> retrieveBySecond(Connection connection, long secondId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(getSelectByFirstQuery(),
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setLong(1, secondId);
            try (ResultSet resultSet = statement.executeQuery()) {
                Dml<F> firstDml = getFirstDml();
                return firstDml.retrieveEntities(resultSet);
            }
        }
    }

    protected abstract String getSelectByFirstQuery();

    protected abstract Dml<F> getFirstDml();

    public PreparedStatement getSelectStatement(Connection connection, long firstId, long secondId) throws
            SQLException {
        PreparedStatement statement = connection.prepareStatement(getSelectQuery(), ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setLong(1, firstId);
        statement.setLong(2, secondId);
        return statement;
    }

    protected abstract String getSelectQuery();

    public abstract void updateRow(ResultSet resultSet, long firstId, long secondId) throws SQLException;
}

