package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;


import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

public class DaoImpl<E extends Entity> implements Dao<E> {
    protected final ConnectionPool connectionPool;
    protected final Dml<E> dml;

    public DaoImpl(ConnectionPool connectionPool, Dml<E> dml) {
        this.connectionPool = connectionPool;
        this.dml = dml;
    }

    @Override
    public E select(long id) {
        E entity = dml.getNullEntity();
        entity.setId(id);
        return select(entity);
    }

    @Override
    public E select(E entityToSelect) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, entityToSelect, false)) {
            return select(statement);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private E select(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return dml.getNullEntity();
            }
            checkOneRecordSelected(resultSet);
            return dml.selectFromRow(resultSet);
        }
    }

    @Override
    public boolean create(E entity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, entity, true);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            E storedEntity = getNullEntity();
            dml.updateRow(resultSet, entity, storedEntity);
            resultSet.insertRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(E entity, E storedEntity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statementForUpdate = dml.getSelect(connection, storedEntity, true);
             ResultSet resultSet = statementForUpdate.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            checkOneRecordSelected(resultSet);
            dml.updateRow(resultSet, entity, storedEntity);
            resultSet.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(E entity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, entity, true);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            resultSet.deleteRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Collection<E> selectAll() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, null, false);
             ResultSet resultSet = statement.executeQuery()) {
            Collection<E> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(dml.selectViewFromRow(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void checkEntity(E entity) {
        if (isNull(entity) || dml.getNullEntity().equals(entity)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public E getNullEntity() {
        return dml.getNullEntity();
    }

    @Override
    public E approveFromStorage(E entity) {
        checkEntity(entity);
        if (entity.getId() == 0) {
            Connection connection;
            try {
                connection = connectionPool.getConnection();
                PreparedStatement statement = dml.getSelect(connection, entity, false);
                E readEntity = select(statement);
                checkEntity(readEntity);
                return readEntity;
            } catch (SQLException e) {
                e.printStackTrace();
                return getNullEntity();
            }
        } else {
            return entity;
        }
    }

    private void checkOneRecordSelected(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            throw new IllegalStateException("statement returned more then one row");
        } else {
            resultSet.previous();
        }
    }
}
