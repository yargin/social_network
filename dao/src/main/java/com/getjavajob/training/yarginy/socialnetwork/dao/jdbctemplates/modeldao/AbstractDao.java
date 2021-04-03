package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.dao.DataIntegrityViolationException;
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

public abstract class AbstractDao<E extends Model> implements Dao<E> {
    private static final String WHERE = " WHERE ";
    protected final transient JdbcTemplate template;
    protected final transient SimpleJdbcInsert jdbcInsert;
    private final transient NamedParameterJdbcTemplate namedTemplate;
    private final String alias;
    private final String tableName;

    protected AbstractDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                          String table, String tableAlias) {
        this.template = template;
        this.jdbcInsert = jdbcInsert;
        if (table.contains("`")) {
            this.jdbcInsert.withTableName(table.substring(1, table.length() - 1));
        } else {
            this.jdbcInsert.withTableName(table);
        }
        this.namedTemplate = namedTemplate;
        this.tableName = table;
        alias = tableAlias;
    }

    @Override
    public E select(long id) {
        String query = getSelectAllQuery() + WHERE + getStringPkAsParameters(alias);
        try {
            return template.queryForObject(query, getRowMapper(), id);
        } catch (TransientDataAccessException e) {
            throw new IllegalStateException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullModel();
        }
    }

    @Override
    public E select(E entity) {
        String query = getSelectAllQuery() + WHERE + getStringAltKeysAsParameters(alias);
        try {
            return template.queryForObject(query, getRowMapper(), getObjectAltKeys(entity));
        } catch (TransientDataAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (EmptyResultDataAccessException e) {
            return getNullModel();
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
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract MapSqlParameterSource createEntityFieldsMap(E entity);

    @Override
    public boolean update(E entity, E storedEntity) {
        UpdateValuesPlacer valuesPlacer = getValuePlacer(entity, storedEntity);
        try {
            return namedTemplate.update(valuesPlacer.getQuery(), valuesPlacer.getParameters()) == 1;
        } catch (IllegalArgumentException e) {
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    protected abstract UpdateValuesPlacer getValuePlacer(E entity, E storedEntity);

    @Override
    public boolean delete(E entity) {
        return template.update(getDeleteByIdQuery(), getObjectPrimaryKeys(entity)) == 1;
    }

    protected String getDeleteByIdQuery() {
        return "DELETE FROM " + getTable(alias) + WHERE + getStringPkAsParameters(alias);
    }

    protected String getDeleteByAltKeysQuery() {
        return "DELETE FROM " + getTable(alias) + WHERE + getStringAltKeysAsParameters(alias);
    }

    protected abstract Object[] getObjectPrimaryKeys(E entity);

    @Override
    public Collection<E> selectAll() {
        String query = getSelectAllQuery();
        return template.query(query, getViewRowMapper());
    }

    protected abstract String getSelectAllQuery();

    public abstract RowMapper<E> getViewRowMapper();

    public abstract RowMapper<E> getRowMapper();

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

    public String getTable(String alias) {
        return tableName + " as " + alias;
    }

    private interface Appender {
        boolean append(StringBuilder builder, boolean firstIteration, String value, String alias);
    }
}
