package com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.OwnedEntity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.isNull;

public class OwnedModelDaoImpl<O extends Entity, E extends OwnedEntity<O>> implements OwnedModelDao<O, E> {
    protected final ConnectionPool connectionPool;
    protected final OwnedEntityDml<O, E> dml;
    protected final Dao<O> ownerDao;

    public OwnedModelDaoImpl(ConnectionPool connectionPool, OwnedEntityDml<O, E> dml, Dao<O> ownerDao) {
        this.connectionPool = connectionPool;
        this.dml = dml;
        this.ownerDao = ownerDao;
    }

    @Override
    public E select(O owner) {
        ownerDao.checkEntity(owner);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, owner);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return dml.getNullEntity();
            }
            return dml.selectFromRow(resultSet, owner);
        } catch (SQLException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean create(E ownedEntity) {
        ownerDao.checkEntity(ownedEntity.getOwner());
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, ownedEntity.getOwner());
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            resultSet.moveToInsertRow();
            dml.updateRow(resultSet, ownedEntity);
            resultSet.insertRow();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean update(E ownedEntity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, ownedEntity.getOwner());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            dml.updateRow(resultSet, ownedEntity);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(E ownedEntity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, ownedEntity.getOwner());
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

    @Override
    public void checkEntity(E ownedEntity) {
        if (isNull(ownedEntity) || dml.getNullEntity().equals(ownedEntity)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public E getNullEntity() {
        return dml.getNullEntity();
    }
}
