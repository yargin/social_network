package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.batchqueryandparamplacer;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class BatchParametersPlacer {
    public <V> boolean addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column, int type,
                                         MapSqlParameterSource parameters) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            parameters.addValue(column, value, type);
            return true;
        }
        return false;
    }

    public <V, T> boolean addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column,
                                            int type, Function<V, T> objToSqlTransformer, MapSqlParameterSource
                                                    parameters) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            if (value == null) {
                parameters.addValue(column, null, type);
            } else {
                parameters.addValue(column, objToSqlTransformer.apply(value), type);
            }
            return true;
        }
        return false;
    }

    public <V> void addValue(Supplier<V> valueGetter, String key, int type, MapSqlParameterSource parameters) {
        parameters.addValue(key, valueGetter.get(), type);
    }

    public <V, T> void addValue(Supplier<V> valueGetter, String key, int type, Function<V, T> objToSqlTransformer,
                                MapSqlParameterSource parameters) {
        V value = valueGetter.get();
        if (value == null) {
            parameters.addValue(key, null, type);
        } else {
            parameters.addValue(key, objToSqlTransformer.apply(value), type);
        }
    }
}
