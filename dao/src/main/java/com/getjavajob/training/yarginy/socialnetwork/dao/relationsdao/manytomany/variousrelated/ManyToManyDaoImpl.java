package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ManyToManyDaoImpl<F extends Entity, S extends Entity> implements ManyToManyDao<F, S> {
    private final ConnectionPool connectionPool;
    private final ManyToManyDml<F, S> manyToManyDml;
    private final Dao<F> firstDao;
    private final Dao<S> secondDao;

    public ManyToManyDaoImpl(ConnectionPool connectionPool, ManyToManyDml<F, S> manyToManyDml, Dao<F> firstDao, Dao<S> secondDao) {
        this.connectionPool = connectionPool;
        this.manyToManyDml = manyToManyDml;
        this.firstDao = firstDao;
        this.secondDao = secondDao;
    }

    @Override
    public Collection<S> selectByFirst(F first) {
        long id = firstDao.approveFromStorage(first).getId();
        try (Connection connection = connectionPool.getConnection()) {
            return manyToManyDml.selectByFirst(connection, id);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<F> selectBySecond(S second) {
        long id = secondDao.approveFromStorage(second).getId();
        try (Connection connection = connectionPool.getConnection()) {
            return manyToManyDml.selectBySecond(connection, id);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean relationExists(F first, S second) {
        long firstId = firstDao.approveFromStorage(first).getId();
        long secondId = secondDao.approveFromStorage(second).getId();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, firstId, secondId);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean create(F first, S second) {
        long firstId = firstDao.approveFromStorage(first).getId();
        long secondId = secondDao.approveFromStorage(second).getId();
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
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(F first, S second) {
        long firstId = firstDao.approveFromStorage(first).getId();
        long secondId = secondDao.approveFromStorage(second).getId();
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
            throw new IllegalStateException(e);
        }
    }
}
