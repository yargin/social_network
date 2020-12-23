package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Collection;

import static java.util.Objects.isNull;

public abstract class AbstractDao<E extends Entity> implements Dao<E> {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public AbstractDao(DataSource dataSource, String table) {
        template = new JdbcTemplate(dataSource);
        namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource);
        jdbcInsert.withTableName(table);
    }

    protected abstract String getSelectByPKeyQuery();

    @Override
    public E select(long id) {
        String query = getSelectByPKeyQuery();
        try {
            return template.queryForObject(query, getRowMapper(), id);
        } catch (TransientDataAccessException e) {
            throw new IllegalStateException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    @Override
    public boolean create(E entity) {
        try {
            MapSqlParameterSource parameters = createEntityFieldsMap(entity);
            return jdbcInsert.execute(parameters) == 1;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public E select(E entity) {
        String query = getSelectByAltKeysQuery();
        try {
            return template.queryForObject(query, getRowMapper(), getAltKeys(entity));
        } catch (TransientDataAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    protected abstract String getSelectByAltKeysQuery();

    protected abstract Object[] getAltKeys(E entity);

    protected abstract MapSqlParameterSource createEntityFieldsMap(E entity);

    @Override
    public boolean update(E entity, E storedEntity) {
        ValuePlacer valuePlacer = getValuePlacer(entity, storedEntity);
        try {
            return namedTemplate.update(valuePlacer.getQuery(), valuePlacer.getParameters()) == 1;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    protected abstract ValuePlacer getValuePlacer(E entity, E storedEntity);

    @Override
    public boolean delete(E entity) {
        String query = getDeleteByPrimaryKeyQuery();
        return template.update(query, getPrimaryKeys(entity)) == 1;
    }

    protected abstract Object[] getPrimaryKeys(E entity);

    protected abstract String getDeleteByPrimaryKeyQuery();

    @Override
    public Collection<E> selectAll() {
        String query = getSelectAllQuery();
        return template.query(query, getViewRowMapper());
    }

    protected abstract String getSelectAllQuery();

    @Override
    public void checkEntity(E entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E approveFromStorage(E entity) {
        throw new UnsupportedOperationException();
    }

    public RowMapper<E> getViewRowMapper() {
        return (resultSet, i) -> getViewExtractor().extractData(resultSet);
    }

    public RowMapper<E> getRowMapper() {
        return (resultSet, i) -> getExtractor().extractData(resultSet);
    }

    public ResultSetExtractor<E> getViewExtractor() {
        return getViewExtractor(null);
    }

    public ResultSetExtractor<E> getViewExtractor(String suffix) {
        return getSuffixedViewExtractor(isNull(suffix) ? "" : suffix);
    }

    public abstract ResultSetExtractor<E> getSuffixedViewExtractor(String suffix);

    public ResultSetExtractor<E> getExtractor() {
        return getExtractor(null);
    }

    public ResultSetExtractor<E> getExtractor(String suffix) {
        return getSuffixedExtractor(isNull(suffix) ? "" : suffix);
    }

    public abstract ResultSetExtractor<E> getSuffixedExtractor(String suffix);
}
