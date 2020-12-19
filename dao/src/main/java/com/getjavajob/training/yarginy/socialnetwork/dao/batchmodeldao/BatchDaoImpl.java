package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImplOld;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class BatchDaoImpl<E extends Entity> extends DaoImplOld<E> implements BatchDao<E> {
    private final BatchDml<E> batchDml;

    public BatchDaoImpl(DataSource dataSource, BatchDml<E> batchDml) {
        super(dataSource, batchDml);
        this.batchDml = batchDml;
    }

    @Override
    public boolean create(Collection<E> entities) {
        if (entities.isEmpty()) {
            return true;
        }
        try (Connection connection = dataSource.getConnection();
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
            return false;
        }
    }

    @Override
    public boolean delete(Collection<E> entities) {
        if (entities.isEmpty()) {
            return true;
        }
        try (Connection connection = dataSource.getConnection();
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
        try {
            if (!delete(entitiesToDelete)) {
                throw new AssertionError();
            }
            newEntities.removeAll(storedEntities);
            if (!create(newEntities)) {
                throw new AssertionError();
            }
        } catch (AssertionError e) {
            return false;
        }
        return true;
    }
}
