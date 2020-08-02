package com.getjavajob.training.yarginy.socialnetwork.dao.models;


import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractEntityDao<E extends Entity> implements EntityDao<E> {
    protected final DbConnector dbConnector;
    protected final EntitySql<E> entitySql;

    public AbstractEntityDao(DbConnector dbConnector, EntitySql<E> entitySql) {
        this.dbConnector = dbConnector;
        this.entitySql = entitySql;
    }

    @Override
    public E select(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entitySql.getSelectStatement(connection, id);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return getNullEntity();
            }
            return entitySql.selectFromRow(resultSet);

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public E select(String identifier) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entitySql.getSelectStatement(connection, identifier);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return getNullEntity();
            }
            return entitySql.selectFromRow(resultSet);

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean create(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entitySql.getSelectStatement(connection, entity.getIdentifier());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            entitySql.updateRow(resultSet, entity);
            resultSet.insertRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean update(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entitySql.getSelectStatement(connection, entity.getIdentifier());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            entitySql.updateRow(resultSet, entity);
            resultSet.updateRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public boolean delete(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entitySql.getSelectStatement(connection, entity.getIdentifier());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            resultSet.deleteRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
