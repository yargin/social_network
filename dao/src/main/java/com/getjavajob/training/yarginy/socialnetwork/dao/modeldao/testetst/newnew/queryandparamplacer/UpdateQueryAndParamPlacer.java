package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.Initializer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryplacers.UpdateQueryPlacer;

public class UpdateQueryAndParamPlacer<E extends Entity> extends QueryAndParamPlacer {
    public UpdateQueryAndParamPlacer(String table, Initializer<E> initializer, E entity, E storedEntity, String[] pKey) {
        super(new UpdateQueryPlacer(table, pKey, KEY_PREFIX));
        initializer.setPlacer(this);
        initializer.initParams(entity, storedEntity);
        initializer.setPKey(storedEntity);
    }
}
