package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.batchqueryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.QueryAndParamPlacer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryplacers.QueryPlacer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public abstract class BatchQueryAndParameterPlacer extends QueryAndParamPlacer {
    public BatchQueryAndParameterPlacer(QueryPlacer queryPlacer) {
        super(queryPlacer);
    }

    public MapSqlParameterSource getInitedParams() {
        return placer.getParameters();
    }
}
