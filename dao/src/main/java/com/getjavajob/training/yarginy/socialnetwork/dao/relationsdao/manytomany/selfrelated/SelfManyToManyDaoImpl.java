package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class SelfManyToManyDaoImpl<E extends Entity> implements SelfManyToManyDao<E> {
    private final ConnectionPool connectionPool;
    private final SelfManyToManyDml<E> manyToManyDml;
    private final Dao<E> dao;

    public SelfManyToManyDaoImpl(ConnectionPool connectionPool, SelfManyToManyDml<E> manyToManyDml, Dao<E> dao) {
        this.connectionPool = connectionPool;
        this.manyToManyDml = manyToManyDml;
        this.dao = dao;
    }

    @Override
    public Collection<E> select(E entity) {
        dao.checkEntity(entity);
        try (Connection connection = connectionPool.getConnection()) {
            return manyToManyDml.select(connection, entity.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean create(E first, E second) {
        dao.checkEntity(first);
        dao.checkEntity(second);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, first.getId(), second.getId());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            manyToManyDml.updateRow(resultSet, first.getId(), second.getId());
            resultSet.insertRow();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(E first, E second) {
        dao.checkEntity(first);
        dao.checkEntity(second);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, first.getId(), second.getId());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            resultSet.deleteRow();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
