package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryplacers.InsertQueryPlacer;

public class InsertQueryAndParamPlacer<E extends Entity> extends QueryAndParamPlacer {
    public InsertQueryAndParamPlacer(String table, Initializer<E> initializer, E entity, E storedEntity) {
        super(new InsertQueryPlacer(table));
        initializer.setPlacer(this);
        initializer.initParams(entity, storedEntity);
    }
}
