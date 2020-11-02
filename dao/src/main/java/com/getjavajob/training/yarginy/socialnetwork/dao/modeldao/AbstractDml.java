package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public abstract class AbstractDml<E extends Entity> implements Dml<E> {
    /**
     * stores value by <b>updater</b> if it differs from stored
     *
     * @param valueGetter       method getting value needed to store
     * @param storedValueGetter method getting stored value
     * @param updater           method that stores value
     * @param column            name used by updater method
     * @param <V>               value's type
     * @throws SQLException if updater failed to store field
     */
    protected <V> void updateFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter,
                                            ResultSetUpdater<V> updater, String column) throws SQLException {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            updater.update(column, value);
        }
    }

    /**
     * stores value by <b>updater</b> if it differs from stored
     *
     * @param valueGetter         method getting value needed to store
     * @param storedValueGetter   method getting stored value
     * @param updater             method that stores value
     * @param column              name used by updater method
     * @param objToSqlTransformer transforms object to eligible type for storing
     * @param <V>                 value's type
     * @param <T>                 object's type
     * @throws SQLException if updater failed to store field
     */
    protected <V, T> void updateFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter,
                                               ResultSetUpdater<T> updater, String column,
                                               Function<V, T> objToSqlTransformer) throws SQLException {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
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

    public PreparedStatement createPreparedStatement(Connection connection, String query, boolean updatable) throws
            SQLException {
        return connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                updatable ? ResultSet.CONCUR_UPDATABLE : ResultSet.CONCUR_READ_ONLY);
    }

    protected abstract String getSelectById();

    protected abstract String getSelectAll();

    protected abstract String getSelectByAltKey();

    protected abstract String getSelectForUpdate();

    protected abstract void setAltKeyParams(PreparedStatement statement, E entity) throws SQLException;

    public PreparedStatement getSelect(Connection connection, E entity, boolean updatable) throws SQLException {
        PreparedStatement statement;
        long id;
        try {
            id = entity.getId();
        } catch (UnsupportedOperationException | NullPointerException e) {
            id = 0;
        }
        if (!updatable && isNull(entity)) {
            statement = createPreparedStatement(connection, getSelectAll(), false);
        } else if (!updatable && id > 0) {
            statement = createPreparedStatement(connection, getSelectById(), false);
            statement.setLong(1, id);
        } else if (!updatable) {
            statement = createPreparedStatement(connection, getSelectByAltKey(), false);
            setAltKeyParams(statement, entity);
        } else {
            statement = createPreparedStatement(connection, getSelectForUpdate(), true);
            setAltKeyParams(statement, entity);
        }
        return statement;
    }
}
