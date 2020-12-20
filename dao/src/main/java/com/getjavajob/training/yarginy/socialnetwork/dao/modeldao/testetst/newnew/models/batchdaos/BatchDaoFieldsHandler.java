package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.batchdaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos.DaoFieldsHandler;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.QueryAndParamPlacer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Collection;

public interface BatchDaoFieldsHandler<E extends Entity> extends DaoFieldsHandler<E> {
    MapSqlParameterSource[] getAltKeyParameter(Collection<E> entity);

    QueryAndParamPlacer getInsertQueryAndParameters(Collection<E> entity);
}
