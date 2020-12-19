package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.queryandparamplacer.ValuePlacer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;

@Component("newAccountDao")
public class DaoImpl implements Dao<Account> {
    private static final AccountDaoFieldsHandler HANDLER = new AccountDaoFieldsHandler();
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public DaoImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Account select(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        try {
            return template.queryForObject(HANDLER.getSelectByIdQuery(), parameters, HANDLER::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    @Override
    public Account select(Account entityToSelect) {
        MapSqlParameterSource parameters = HANDLER.getAltKeyParameter(entityToSelect);
        try {
            return template.queryForObject(HANDLER.getSelectByAltKeyQuery(), parameters, HANDLER::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    @Override
    public boolean create(Account entity) {
        ValuePlacer queryAndParameters = HANDLER.getInsertQueryAndParameters(entity, getNullEntity());
        String query = queryAndParameters.getQuery();
        MapSqlParameterSource parameters = queryAndParameters.getInitedParams();
        try {
            return template.update(query, parameters) == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean update(Account entity, Account storedEntity) {
        ValuePlacer queryAndParameters = HANDLER.getUpdateQueryAndParameters(entity, storedEntity);
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
    public boolean delete(Account entity) {
        MapSqlParameterSource parameters = HANDLER.getAltKeyParameter(entity);
        return template.update(HANDLER.getDeleteQuery(), parameters) > 0;
    }

    @Override
    public Collection<Account> selectAll() {
        return template.query(HANDLER.getSelectAllQuery(), HANDLER::mapViewRow);
    }

    @Override
    public void checkEntity(Account entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Account getNullEntity() {
        return HANDLER.getNullEntity();
    }

    @Override
    public Account approveFromStorage(Account entity) {
        throw new UnsupportedOperationException();
    }
}
