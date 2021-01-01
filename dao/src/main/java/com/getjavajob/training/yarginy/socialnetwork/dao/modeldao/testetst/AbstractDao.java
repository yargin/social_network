package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class AbstractDao<E extends Entity> implements Dao<E> {
    private final NamedParameterJdbcTemplate namedTemplate;
    protected final JdbcTemplate template;
    protected final SimpleJdbcInsert jdbcInsert;
    private final String alias;
    private final String where = " WHERE ";
    private final String tableName;

    public AbstractDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                       String table, String tableAlias) {
        this.template = template;
        this.jdbcInsert = jdbcInsert;
        this.namedTemplate = namedTemplate;
        jdbcInsert.withTableName(table);
        this.tableName = table;
        alias = tableAlias;
    }

    @Override
    public E select(long id) {
        String query = getSelectAllQuery() + where + getStringPkAsParameters(alias);
        try {
            return template.queryForObject(query, getRowMapper(), id);
        } catch (TransientDataAccessException e) {
            throw new IllegalStateException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    @Override
    public E select(E entity) {
        String query = getSelectAllQuery() + where + getStringAltKeysAsParameters(alias);
        try {
            return template.queryForObject(query, getRowMapper(), getObjectAltKeys(entity));
        } catch (TransientDataAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullEntity();
        }
    }

    protected abstract Object[] getObjectAltKeys(E entity);

    @Override
    public boolean create(E entity) {
        try {
            MapSqlParameterSource parameters = createEntityFieldsMap(entity);
            return jdbcInsert.execute(parameters) == 1;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

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
        ;
        return template.update(getDeleteQuery(), getObjectAltKeys(entity)) == 1;
    }

    protected String getDeleteQuery() {
        return "DELETE FROM " + getTable(alias) + where + getStringAltKeysAsParameters(alias);
    }

    protected abstract Object[] getObjectPrimaryKeys(E entity);

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

    public abstract RowMapper<E> getViewRowMapper();

    public abstract RowMapper<E> getRowMapper();

    //test table

    protected abstract String[] getFieldsList();

    public String getFields(String alias) {
        return buildString(this::getFieldsList, this::appendField, alias);
    }

    protected abstract String[] getViewFieldsList();

    public String getViewFields(String alias) {
        return buildString(this::getViewFieldsList, this::appendField, alias);
    }

    private boolean appendField(StringBuilder stringBuilder, boolean firstIteration, String field, String alias) {
        String optionalAlias = alias.isEmpty() ? alias : alias + '.';
        if (!firstIteration) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(optionalAlias).append(field).append(" as ").append(field).append(alias);
        return false;
    }

    private String buildString(Supplier<String[]> fieldsGetter, Appender appender, String alias) {
        String[] fields = fieldsGetter.get();
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstIteration = true;
        for (String field : fields) {
            firstIteration = appender.append(stringBuilder, firstIteration, field, alias);
        }
        return stringBuilder.toString();
    }

    public String getStringPkAsParameters(String alias) {
        return buildString(this::getPrimaryKeys, this::appendKey, alias);
    }

    public abstract String[] getPrimaryKeys();

    public String getStringAltKeysAsParameters(String alias) {
        return buildString(this::getAltKeys, this::appendKey, alias);
    }

    public abstract String[] getAltKeys();

    private boolean appendKey(StringBuilder stringBuilder, boolean firstIteration, String key, String alias) {
        if (!firstIteration) {
            stringBuilder.append(" AND ");
        }
        if (!alias.isEmpty()) {
            stringBuilder.append(alias).append('.');
        }
        stringBuilder.append(key).append(" = ? ");
        return false;
    }

    private interface Appender {
        boolean append(StringBuilder builder, boolean firstIteration, String value, String alias);
    }

    public String getTable(String alias) {
        return tableName + " as " + alias;
    }
}
