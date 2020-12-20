package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryplacers.InsertQueryPlacer;

//@Component
//@Scope("prototype")
public class InsertQueryAndParamPlacer<E extends Entity> extends QueryAndParamPlacer {
    public InsertQueryAndParamPlacer(String table, Initializer<E> initializer, E entity) {
        super(new InsertQueryPlacer(table));
        initializer.setPlacer(this);
        initializer.initInsertParams(entity);
    }
}
