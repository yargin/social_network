package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class SelfManyToManyDaoImpl<E extends Entity> implements SelfManyToManyDao<E> {
    private final ConnectionPool connectionPool;
    private final SelfManyToManyDml<E> manyToManyDml;

    public SelfManyToManyDaoImpl(ConnectionPool connectionPool, SelfManyToManyDml<E> manyToManyDml) {
        this.connectionPool = connectionPool;
        this.manyToManyDml = manyToManyDml;
    }

    @Override
    public Collection<E> select(long id) {
        try (Connection connection = connectionPool.getConnection()) {
            return manyToManyDml.retrieve(connection, id);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean relationExists(long firstId, long secondId) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, firstId, secondId);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean create(long firstId, long secondId) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, firstId, secondId);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            manyToManyDml.updateRow(resultSet, firstId, secondId);
            resultSet.insertRow();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean delete(long firstId, long secondId) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, firstId, secondId);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            resultSet.deleteRow();
            if (resultSet.next()) {
                throw new IllegalStateException("statement returned more then one row");
            }
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
