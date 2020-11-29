package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public abstract class BatchDeletionHelper {
    private BatchDeletionHelper() {
    }

    public static <E extends Entity> PreparedStatement batchSelectUpdate(Connection connection, Collection<E> entities,
                                                                         String table, String idColumn)
            throws SQLException {
        StringBuilder numberBuilder = new StringBuilder();
        String select;
        if (entities.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            for (E entity : entities) {
                long id = entity.getId();
                if (id < 1) {
                    throw new IllegalArgumentException("cannot delete entity that wasn't read from storage");
                }
                numberBuilder.append(entity.getId()).append(", ");
            }
            String numbers = numberBuilder.substring(0, numberBuilder.length() - 2);
            select = buildQuery().select(table).whereIn(new String[]{idColumn}, new String[]{numbers}).build();
        }
        return connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
}
