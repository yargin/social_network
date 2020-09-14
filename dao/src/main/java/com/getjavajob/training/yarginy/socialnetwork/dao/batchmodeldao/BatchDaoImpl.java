package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class BatchDaoImpl<E extends Entity> extends DaoImpl<E> implements BatchDao<E> {
    private final BatchDml<E> batchDml;

    public BatchDaoImpl(DbConnector dbConnector, BatchDml<E> batchDml) {
        super(dbConnector, batchDml);
        this.batchDml = batchDml;
    }

    @Override
    public boolean create(Collection<E> entities) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = batchDml.batchSelect(connection, entities);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return false;
            }
            for (E entity : entities) {
                resultSet.moveToInsertRow();
                abstractDml.updateRow(resultSet, entity);
                resultSet.insertRow();
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
