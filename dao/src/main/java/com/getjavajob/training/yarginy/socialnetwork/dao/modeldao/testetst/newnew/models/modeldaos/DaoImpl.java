package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.modeldaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.valuesplacer.queryandparamplacer.ValuePlacer;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class DaoImpl<E extends Entity> implements Dao<E> {
    protected final DaoFieldsHandler<E> handler;
    protected final JdbcTemplate template;
    protected final NamedParameterJdbcTemplate namedTemplate;
    protected final SimpleJdbcInsert insertTemplate;

    public DaoImpl(JdbcTemplate template, DaoFieldsHandler<E> handler) {
        this.template = template;
        this.handler = handler;
        insertTemplate = new SimpleJdbcInsert(template);
        insertTemplate.withTableName(handler.getTableName());
        namedTemplate = new NamedParameterJdbcTemplate(template);
    }

    @Override
    public E select(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        try {
            return namedTemplate.queryForObject(handler.getSelectByIdQuery(), parameters, handler::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return handler.getNullEntity();
        }
    }

    @Override
    public E select(E entityToSelect) {
        template.query("SELECT * FROM ...", new Object[]{}, new ResultSetExtractor<E>() {
            @Override
            public E extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return null;
            }
        });
//        ResultSetExtractor
        MapSqlParameterSource parameters = handler.getAltKeyParameter(entityToSelect);
        try {
            return namedTemplate.queryForObject(handler.getSelectByAltKeyQuery(), parameters, handler::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return handler.getNullEntity();
        }
    }

    @Override
    public boolean create(E entity) {
        ValuePlacer queryAndParameters = handler.getInsertQueryAndParameters(entity);
        try {
            return insertTemplate.execute(queryAndParameters.getInitedParams()) == 1;
        } catch (DuplicateKeyException e) {
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
        MapSqlParameterSource parameters = handler.getPKeyParameter(entity);
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
