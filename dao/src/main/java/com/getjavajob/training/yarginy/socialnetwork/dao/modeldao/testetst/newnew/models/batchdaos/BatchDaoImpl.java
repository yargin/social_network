package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.batchdaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos.DaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.ValuePlacer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collection;

public class BatchDaoImpl<E extends Entity> extends DaoImpl<E> implements BatchDao<E> {
    private final BatchDaoFieldsHandler<E> handler;

    public BatchDaoImpl(NamedParameterJdbcTemplate template, BatchDaoFieldsHandler<E> handler) {
        super(template, handler);
        this.handler = handler;
    }

    @Override
    public boolean create(Collection<E> entities) {
        ValuePlacer queryAndParameters = handler.getInsertQueryAndParameters(entities);
        String query = queryAndParameters.getQuery();
        MapSqlParameterSource parameters = queryAndParameters.getInitedParams();
        try {
            return template.update(query, parameters) == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Collection<E> entities) {
        MapSqlParameterSource[] parameters = handler.getAltKeyParameter(entities);
        return template.batchUpdate(handler.getDeleteQuery(), parameters).length > 0;
    }
}
