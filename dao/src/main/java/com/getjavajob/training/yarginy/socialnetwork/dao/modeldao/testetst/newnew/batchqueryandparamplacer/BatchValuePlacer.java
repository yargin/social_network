package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.batchqueryandparamplacer;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.ValuePlacer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface BatchValuePlacer extends ValuePlacer {
    MapSqlParameterSource[] getInitedParams();
}
