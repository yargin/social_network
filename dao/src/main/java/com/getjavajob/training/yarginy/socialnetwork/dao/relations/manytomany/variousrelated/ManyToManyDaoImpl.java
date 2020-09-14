package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ManyToManyDaoImpl<F extends Entity, S extends Entity> implements ManyToManyDao<F, S> {
    private final DbConnector dbConnector;
    private final ManyToManyDml<F, S> manyToManyDml;
    private final Dao<F> firstDao;
    private final Dao<S> secondDao;

    public ManyToManyDaoImpl(DbConnector dbConnector, ManyToManyDml<F, S> manyToManyDml, Dao<F> firstDao, Dao<S> secondDao) {
        this.dbConnector = dbConnector;
        this.manyToManyDml = manyToManyDml;
        this.firstDao = firstDao;
        this.secondDao = secondDao;
    }

    @Override
    public Collection<S> selectByFirst(F first) {
        firstDao.checkEntity(first);
        try (Connection connection = dbConnector.getConnection()) {
            return manyToManyDml.selectByFirst(connection, first.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<F> selectBySecond(S second) {
        secondDao.checkEntity(second);
        try (Connection connection = dbConnector.getConnection()) {
            return manyToManyDml.selectBySecond(connection, second.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean create(F first, S second) {
        firstDao.checkEntity(first);
        secondDao.checkEntity(second);
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, first.getId(), second.getId());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            manyToManyDml.updateRow(resultSet, first.getId(), second.getId());
            resultSet.insertRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(F first, S second) {
        firstDao.checkEntity(first);
        secondDao.checkEntity(second);
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, first.getId(), second.getId());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            resultSet.deleteRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
