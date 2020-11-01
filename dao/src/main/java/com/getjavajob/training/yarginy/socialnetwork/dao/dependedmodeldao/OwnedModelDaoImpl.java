package com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.models.OwnedEntity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
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
            checkOneRecordSelected(resultSet);
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
            dml.updateRow(resultSet, ownedEntity, getNullEntity());
            resultSet.insertRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(E ownedEntity, E storedOwnedEntity) {
//        try (Connection connection = connectionPool.getConnection();
//             PreparedStatement statement = dml.getSelect(connection, ownedEntity.getOwner());
//             ResultSet resultSet = statement.executeQuery()) {
//            if (!resultSet.next()) {
//                return false;
//            }
//            dml.updateRow(resultSet, ownedEntity, storedOwnedEntity);
//            return true;
//        } catch (SQLException e) {
//            return false;
//        }
        //todo
        boolean performed = true;
        if (!ownedEntity.equals(storedOwnedEntity)) {
            if (!delete(storedOwnedEntity)) {
                return false;
            }
            performed = create(ownedEntity);
        }
        return performed;
    }

    @Override
    public boolean delete(E ownedEntity) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = dml.getSelect(connection, ownedEntity.getOwner());
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return false;
            }
            checkOneRecordSelected(resultSet);
            resultSet.deleteRow();
            return true;
        } catch (SQLException e) {
            return false;
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

    private void checkOneRecordSelected(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            throw new IllegalStateException("statement returned more then one row");
        } else {
            resultSet.previous();
        }
    }
}
