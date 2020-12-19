package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.batchqueryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryplacers.InsertQueryPlacer;

import java.util.Collection;

public class BatchInsertQueryAndParamPlacer<E extends Entity> extends BatchQueryAndParameterPlacer {
    public BatchInsertQueryAndParamPlacer(String table, Initializer<E> initializer, Collection<E> entities) {
        super(new InsertQueryPlacer(table));
        initializer.setPlacer(this);
        for (E entity : entities) {
            initializer.initInsertParams(entity);
        }
    }
}
