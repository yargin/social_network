package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;

@Component("newAccountDao")
public class AccountDao implements Dao<Account> {
    private static final AccountFieldsHandler HANDLER = new AccountFieldsHandler();
    private static final String SELECT_BY_ID = "SELECT * FROM Accounts WHERE id = :id";
    private static final String SELECT_BY_ALT_KEY = "SELECT * FROM Accounts WHERE email = :email";
    private static final String SELECT_ALL = "SELECT * FROM Accounts";
    private static final String DELETE_ACCOUNT = "DELETE FROM Accounts WHERE email = :email";
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public AccountDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Account select(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        try {
            return template.queryForObject(SELECT_BY_ID, parameters, HANDLER::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    @Override
    public Account select(Account entityToSelect) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", entityToSelect.getEmail());
        try {
            return template.queryForObject(SELECT_BY_ALT_KEY, parameters, HANDLER::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    @Override
    public boolean create(Account entity) {
        AbstractQueryAndParameters<Account> queryAndParameters = HANDLER.getInsertQueryAndParameters(entity,
                getNullEntity());
        String query = "INSERT INTO Accounts" + queryAndParameters.getQueryParameters();
        MapSqlParameterSource parameters = queryAndParameters.getParameters();
        try {
            return template.update(query, parameters) == 1;
        } catch (DataAccessException e) {
//            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account entity, Account storedEntity) {
        AbstractQueryAndParameters<Account> queryAndParameters = HANDLER.getUpdateQueryAndParameters(entity,
                storedEntity);
        String query;
        try {
            query = "UPDATE accounts " + queryAndParameters.getQueryParameters() + " WHERE id = :id";
        } catch (IllegalArgumentException e) {
            return false;
        }
        MapSqlParameterSource parameters = queryAndParameters.getParameters();
        parameters.addValue("id", storedEntity.getId());
        return template.update(query, parameters) > 0;
    }

    @Override
    public boolean delete(Account entity) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", entity.getEmail());
        return template.update(DELETE_ACCOUNT, parameters) > 0;
    }

    @Override
    public Collection<Account> selectAll() {
        return template.query(SELECT_ALL, HANDLER::mapViewRow);
    }

    @Override
    public void checkEntity(Account entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Account getNullEntity() {
        return NullEntitiesFactory.getNullAccount();
    }

    @Override
    public Account approveFromStorage(Account entity) {
        throw new UnsupportedOperationException();
    }
}
