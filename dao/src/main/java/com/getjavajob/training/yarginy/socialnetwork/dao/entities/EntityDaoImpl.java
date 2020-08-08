package com.getjavajob.training.yarginy.socialnetwork.dao.entities;


import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityDaoImpl<E extends Entity> implements EntityDao<E> {
    private final DbConnector dbConnector;
    private final EntityDml<E> entityDml;

    public EntityDaoImpl(DbConnector dbConnector, EntityDml<E> entityDml) {
        this.dbConnector = dbConnector;
        this.entityDml = entityDml;
    }

    @Override
    public E select(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entityDml.getSelectStatement(connection, id)) {
            return select(statement);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public E select(String identifier) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entityDml.getSelectStatement(connection, identifier)) {
            return select(statement);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private E select(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return getNullEntity();
            }
            return entityDml.selectFromRow(resultSet);
        }
    }

    @Override
    public boolean create(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entityDml.getSelectStatement(connection, entity.getIdentifier());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            entityDml.updateRow(resultSet, entity);
            resultSet.insertRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean update(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entityDml.getSelectStatement(connection, entity.getIdentifier());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            entityDml.updateRow(resultSet, entity);
            resultSet.updateRow();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = entityDml.getSelectStatement(connection, entity.getIdentifier());
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

    @Override
    public String selectAll() {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(entityDml.getSelectAllQuery());
             ResultSet resultSet = statement.executeQuery()) {
            int columnNumber = resultSet.getMetaData().getColumnCount();
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                for (int i = 1; i <= columnNumber; i++) {
                    stringBuilder.append(resultSet.getObject(i)).append(" ");
                }
                stringBuilder.append(System.lineSeparator());
            }
            return stringBuilder.toString();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public E getNullEntity() {
        return null;
    }
}
