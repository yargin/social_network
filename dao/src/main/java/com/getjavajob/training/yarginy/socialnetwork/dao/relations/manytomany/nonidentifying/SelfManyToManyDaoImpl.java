package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.nonidentifying;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class SelfManyToManyDaoImpl<E extends Entity> implements SelfManyToManyDao<E> {
    private final DbConnector dbConnector;
    private final SelfManyToManyDml<E> manyToManyDml;

    public SelfManyToManyDaoImpl(DbConnector dbConnector, SelfManyToManyDml<E> manyToManyDml) {
        this.dbConnector = dbConnector;
        this.manyToManyDml = manyToManyDml;
    }

    @Override
    public Collection<E> select(E entity) {
        try (Connection connection = dbConnector.getConnection()) {
            return manyToManyDml.select(connection, entity.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean create(E first, E second) {
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
    public boolean delete(E first, E second) {
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
