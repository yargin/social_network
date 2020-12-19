package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.paramplacers;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ParametersPlacer {
    MapSqlParameterSource parameters = new MapSqlParameterSource();

    public <V> boolean addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column, int type) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            parameters.addValue(column, value, type);
            return true;
        }
        return false;
    }

    public <V, T> boolean addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column,
                                            int type, Function<V, T> objToSqlTransformer) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            if (value == null) {
                parameters.addValue(column, value, type);
            } else {
                parameters.addValue(column, objToSqlTransformer.apply(value), type);
            }
            return true;
        }
        return false;
    }

    public <V> void addKey(Supplier<V> valueGetter, String key, int type) {
        parameters.addValue(key, valueGetter.get(), type);
    }

    public <V, T> void addKey(Supplier<V> valueGetter, String key, int type, Function<V, T> objToSqlTransformer) {
        parameters.addValue(key, objToSqlTransformer.apply(valueGetter.get()), type);
    }

    public MapSqlParameterSource getParameters() {
        return parameters;
    }
}
