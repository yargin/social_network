package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ManyToManyDaoImpl<F extends Entity, S extends Entity> implements ManyToManyDao<F, S> {
    private final DataSource dataSource;
    private final ManyToManyDml<F, S> manyToManyDml;

    public ManyToManyDaoImpl(DataSource dataSource, ManyToManyDml<F, S> manyToManyDml) {
        this.dataSource = dataSource;
        this.manyToManyDml = manyToManyDml;
    }

    @Override
    public Collection<S> selectByFirst(long firstId) {
        try (Connection connection = dataSource.getConnection()) {
            return manyToManyDml.retrieveByFirst(connection, firstId);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Collection<F> selectBySecond(long secondId) {
        try (Connection connection = dataSource.getConnection()) {
            return manyToManyDml.retrieveBySecond(connection, secondId);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean relationExists(long firstId, long secondId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = manyToManyDml.getSelectStatement(connection, firstId, secondId);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean create(long firstId, long secondId) {
        try (Connection connection = dataSource.getConnection();
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
        try (Connection connection = dataSource.getConnection();
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
