package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;


import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

public class DaoImpl<E extends Entity> implements Dao<E> {
    protected final DbConnector dbConnector;
    protected final AbstractDml<E> abstractDml;

    public DaoImpl(DbConnector dbConnector, AbstractDml<E> abstractDml) {
        this.dbConnector = dbConnector;
        this.abstractDml = abstractDml;
    }

    @Override
    public E select(long id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = abstractDml.getSelect(connection, id)) {
            return select(statement);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public E select(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = abstractDml.getSelect(connection, entity)) {
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
             PreparedStatement statement = abstractDml.getUpdatableSelect(connection, entity);
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
             PreparedStatement statement = abstractDml.getUpdatableSelect(connection, entity);
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
             PreparedStatement statement = abstractDml.getUpdatableSelect(connection, entity);
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
    public Collection<E> selectAll() {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = abstractDml.getSelectAll(connection);
             ResultSet resultSet = statement.executeQuery()) {
            Collection<E> all = new ArrayList<>();
            while (resultSet.next()) {
                all.add(abstractDml.selectFromRow(resultSet));
            }
            return all;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void checkEntity(E entity) {
        if (isNull(entity) || abstractDml.getNullEntity().equals(entity)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public E getNullEntity() {
        return abstractDml.getNullEntity();
    }
}
