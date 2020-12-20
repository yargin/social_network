package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos.DaoFieldsHandler;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.QueryAndParamPlacer;
import org.springframework.stereotype.Component;

@Component
public abstract class Initializer<E extends Entity> implements DaoFieldsHandler.InnerInitializer<E> {
    protected QueryAndParamPlacer placer;

    public void setPlacer(QueryAndParamPlacer placer) {
        this.placer = placer;
    }
}
