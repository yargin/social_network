package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.queryplacers.QueryParametersPlacer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractQueryAndParameters<E extends Entity> {
    protected final QueryParametersPlacer queryParametersPlacer;
    private final MapSqlParameterSource parameters = new MapSqlParameterSource();

    public AbstractQueryAndParameters(QueryParametersPlacer queryParametersPlacer) {
        this.queryParametersPlacer = queryParametersPlacer;
    }

    public MapSqlParameterSource getParameters() {
        return parameters;
    }

    protected <V> void addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column, int type) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            parameters.addValue(column, value, type);
            queryParametersPlacer.addColumn(column);
        }
    }

    protected <V, T> void addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column,
                                            int type, Function<V, T> objToSqlTransformer) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            if (value == null) {
                parameters.addValue(column, value, type);
            } else {
                parameters.addValue(column, objToSqlTransformer.apply(value), type);
            }
            queryParametersPlacer.addColumn(column);
        }
    }

    public String getQueryParameters() {
        return queryParametersPlacer.getQueryParameters();
    }
}
