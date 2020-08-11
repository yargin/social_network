package com.getjavajob.training.yarginy.socialnetwork.dao.entities;


import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.isNull;

public class DaoImpl<E extends Entity> implements Dao<E> {
    private final DbConnector dbConnector;
    private final AbstractDml<E> abstractDml;

    public DaoImpl(DbConnector dbConnector, AbstractDml<E> abstractDml) {
        this.dbConnector = dbConnector;
        this.abstractDml = abstractDml;
    }

    @Override
    public E select(int id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = abstractDml.getSelectStatement(connection, id)) {
            return select(statement);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public E select(String identifier) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = abstractDml.getSelectStatement(connection, identifier)) {
            return select(statement);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private E select(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return abstractDml.getNullEntity();
            }
            return abstractDml.selectFromRow(resultSet);
        }
    }

    @Override
    public boolean create(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = abstractDml.getSelectStatement(connection, entity.getIdentifier());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            abstractDml.updateRow(resultSet, entity);
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
             PreparedStatement statement = abstractDml.getSelectStatement(connection, entity.getIdentifier());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            abstractDml.updateRow(resultSet, entity);
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
             PreparedStatement statement = abstractDml.getSelectStatement(connection, entity.getIdentifier());
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
             PreparedStatement statement = connection.prepareStatement(abstractDml.getSelectAllQuery());
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
        return abstractDml.getNullEntity();
    }

    @Override
    public void checkEntity(E entity) {
        if (isNull(entity) || abstractDml.getNullEntity().equals(entity)) {
            throw new IllegalArgumentException();
        }
    }
}
