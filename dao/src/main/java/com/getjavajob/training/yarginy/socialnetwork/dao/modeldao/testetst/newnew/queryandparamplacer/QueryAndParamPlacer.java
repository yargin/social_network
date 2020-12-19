package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.paramplacers.ParametersPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryplacers.QueryPlacer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class QueryAndParamPlacer implements ValuePlacer {
    protected static final String KEY_PREFIX = "key";
    private final QueryPlacer queryPlacer;
    private final ParametersPlacer placer;

    public QueryAndParamPlacer(QueryPlacer queryPlacer) {
        this.queryPlacer = queryPlacer;
        placer = new ParametersPlacer();
    }

    @Override
    public <V> void placeKey(Supplier<V> valueGetter, String column, int type) {
        placer.addKey(valueGetter, KEY_PREFIX + column, type);
    }

    @Override
    public <V, T> void placeKey(Supplier<V> valueGetter, String column, int type, Function<V, T> objToSqlTransformer) {
        placer.addKey(valueGetter, KEY_PREFIX + column, type, objToSqlTransformer);
    }

    @Override
    public <V> void placeValueIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column, int type) {
        if (placer.addFieldIfDiffers(valueGetter, storedValueGetter, column, type)) {
            queryPlacer.addColumn(column);
        }
    }

    @Override
    public <V, T> void placeValueIfDiffers(Supplier<V> valueGetter, Supplier<V> storedValueGetter, String column,
                                           int type, Function<V, T> objToSqlTransformer) {
        if (placer.addFieldIfDiffers(valueGetter, storedValueGetter, column, type, objToSqlTransformer)) {
            queryPlacer.addColumn(column);
        }
    }

    @Override
    public MapSqlParameterSource getInitedParams() {
        return placer.getParameters();
    }

    @Override
    public String getQuery() {
        return queryPlacer.getQuery();
    }
}
