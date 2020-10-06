package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public abstract class AbstractDml<E extends Entity> implements Dml<E> {
    protected <V> void updateFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter,
                                            ResultSetUpdater<V> updater, String column) throws SQLException {
        V value = valueGetter.get();
        if (!isNull(value) && !Objects.equals(value, storedValueGetter.get())) {
            updater.update(column, value);
        }
    }

    protected <V, T> void updateFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter,
                                               ResultSetUpdater<T> updater, String column,
                                               Function<V, T> objToSqlTransformer) throws SQLException {
        V value = valueGetter.get();
        if (!isNull(value) && !Objects.equals(value, storedValueGetter.get())) {
            updater.update(column, objToSqlTransformer.apply(value));
        }
    }

    /**
     * represents update-cell-method in {@link ResultSet}
     */
    @FunctionalInterface
    public interface ResultSetUpdater<E> {
        void update(String column, E value) throws SQLException;
    }
}
