package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.function.Function;
import java.util.function.Supplier;

public interface ValuePlacer {
    <V> void placeKey(Supplier<V> valueGetter, String column, int type);

    <V, T> void placeKey(Supplier<V> valueGetter, String column, int type, Function<V, T> objToSqlTransformer);

    <V> void placeValueIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column, int type);

    <V, T> void placeValueIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column, int type,
                                    Function<V, T> objToSqlTransformer);

    /**
     * builds sql query for update
     *
     * @return sql update statement
     * @throws IllegalArgumentException if there's nothing to update - all fields are equal
     */
    String getQuery();

    MapSqlParameterSource getInitedParams();
}
