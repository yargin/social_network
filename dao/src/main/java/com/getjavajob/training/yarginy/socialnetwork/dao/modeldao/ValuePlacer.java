package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ValuePlacer {
    private static final String KEY_PREFIX = "key";
    private final String table;
    private final Collection<String> altKeys = new ArrayList<>();
    private final MapSqlParameterSource parameters = new MapSqlParameterSource();
    private final StringBuilder updateQueryParameters = new StringBuilder();

    public ValuePlacer(String table) {
        this.table = table;
    }

    public <V> void addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column, int type) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            parameters.addValue(column, value, type);
            addColumn(column);
        }
    }

    public <V, T> void addFieldIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column,
                                         int type, Function<V, T> objToSqlTransformer) {
        V value = valueGetter.get();
        if (!Objects.equals(value, storedValueGetter.get())) {
            if (value == null) {
                parameters.addValue(column, null, type);
            } else {
                parameters.addValue(column, objToSqlTransformer.apply(value), type);
            }
            addColumn(column);
        }
    }

    public <V> void addKey(Supplier<V> valueGetter, String column, int type) {
        parameters.addValue(KEY_PREFIX + column, valueGetter.get(), type);
        altKeys.add(column);
    }

    public <V, T> void addKey(Supplier<V> valueGetter, String column, int type, Function<V, T> objToSqlTransformer) {
        V value = valueGetter.get();
        if (value == null) {
            parameters.addValue(KEY_PREFIX + column, null, type);
        } else {
            parameters.addValue(KEY_PREFIX + column, objToSqlTransformer.apply(value), type);
        }
        altKeys.add(column);
    }

    private void addColumn(String column) {
        updateQueryParameters.append(column).append(" = :").append(column).append(", ");
    }


    public MapSqlParameterSource getParameters() {
        return parameters;
    }

    private String addAltKeysToQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : altKeys) {
            stringBuilder.append(key).append(" = :").append(KEY_PREFIX).append(key).append(", ");
        }
        return getStringWithoutLastComma(stringBuilder);
    }

    public String getQuery() {
        return "UPDATE " + table + " SET " + getStringWithoutLastComma(updateQueryParameters) + " WHERE " +
                addAltKeysToQuery();
    }

    private String getStringWithoutLastComma(StringBuilder stringBuilder) {
        int length = stringBuilder.length();
        if (length > 0) {
            return stringBuilder.substring(0, length - 2);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
