package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.ValuePlacer;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collection;

public class DaoImpl<E extends Entity> implements Dao<E> {
    private final DaoFieldsHandler<E> handler;
    private final NamedParameterJdbcTemplate template;

    public DaoImpl(NamedParameterJdbcTemplate template, DaoFieldsHandler<E> handler) {
        this.template = template;
        this.handler = handler;
    }

    @Override
    public E select(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        try {
            return template.queryForObject(handler.getSelectByIdQuery(), parameters, handler::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return handler.getNullEntity();
        }
    }

    @Override
    public E select(E entityToSelect) {
        MapSqlParameterSource parameters = handler.getAltKeyParameter(entityToSelect);
        try {
            return template.queryForObject(handler.getSelectByAltKeyQuery(), parameters, handler::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return handler.getNullEntity();
        }
    }

    @Override
    public boolean create(E entity) {
        ValuePlacer queryAndParameters = handler.getInsertQueryAndParameters(entity);
        String query = queryAndParameters.getQuery();
        MapSqlParameterSource parameters = queryAndParameters.getInitedParams();
        try {
            return template.update(query, parameters) == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean update(E entity, E storedEntity) {
        ValuePlacer queryAndParameters = handler.getUpdateQueryAndParameters(entity, storedEntity);
        String query;
        try {
            query = queryAndParameters.getQuery();
        } catch (IllegalArgumentException e) {
            return false;
        }
        MapSqlParameterSource parameters = queryAndParameters.getInitedParams();
        return template.update(query, parameters) > 0;
    }

    @Override
    public boolean delete(E entity) {
        MapSqlParameterSource parameters = handler.getAltKeyParameter(entity);
        return template.update(handler.getDeleteQuery(), parameters) > 0;
    }

    @Override
    public Collection<E> selectAll() {
        return template.query(handler.getSelectAllQuery(), handler::mapViewRow);
    }

    @Override
    public void checkEntity(E entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getNullEntity() {
        return handler.getNullEntity();
    }

    @Override
    public E approveFromStorage(E entity) {
        throw new UnsupportedOperationException();
    }
}
