package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class BatchDaoImpl<E extends Entity> extends DaoImpl<E> implements BatchDao<E> {
    private final BatchDml<E> batchDml;

    public BatchDaoImpl(ConnectionPool connectionPool, BatchDml<E> batchDml) {
        super(connectionPool, batchDml);
        this.batchDml = batchDml;
    }

    @Override
    public boolean create(Collection<E> entities) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = batchDml.batchSelectUpdate(connection, entities);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            for (E entity : entities) {
                resultSet.moveToInsertRow();
                dml.updateRow(resultSet, entity, getNullEntity());
                resultSet.insertRow();
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
