package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class BatchDaoImpl<E extends Entity> extends DaoImpl<E> implements BatchDao<E> {
    private final BatchDml<E> batchDml;

    public BatchDaoImpl(ConnectionPool connectionPool, BatchDml<E> batchDml) {
        super(connectionPool, batchDml);
        this.batchDml = batchDml;
    }

    @Override
    public boolean create(Collection<E> entities) {
        if (entities.isEmpty()) {
            return true;
        }
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = batchDml.batchSelectForInsert(connection, entities);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            for (E entity : entities) {
                resultSet.moveToInsertRow();
                dml.updateRow(resultSet, entity, getNullEntity());
                resultSet.insertRow();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Collection<E> entities) {
        if (entities.isEmpty()) {
            return true;
        }
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = batchDml.batchSelectForDelete(connection, entities);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                resultSet.deleteRow();
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Collection<E> storedEntities, Collection<E> newEntities) {
        Collection<E> entitiesToDelete = new ArrayList<>(storedEntities);
        entitiesToDelete.removeAll(newEntities);
        delete(entitiesToDelete);
        newEntities.removeAll(storedEntities);
        create(newEntities);
        return true;
    }
}
