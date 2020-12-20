package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.batchqueryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.QueryAndParamPlacer;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BatchQueryAndParameterPlacer {
    private QueryAndParamPlacer firstRowPlacer;

    public BatchQueryAndParameterPlacer(QueryAndParamPlacer firstRowPlacer) {
        this.firstRowPlacer = firstRowPlacer;
    }

    public <V> void placeValue(Supplier<V> valueGetter, String column, int type) {
        firstRowPlacer.placeValue(valueGetter, column, type);
    }

    public <V, T> void placeValue(Supplier<V> valueGetter, String column, int type, Function<V, T> objToSqlTransformer) {
        firstRowPlacer.placeValue(valueGetter, column, type, objToSqlTransformer);
    }
}
