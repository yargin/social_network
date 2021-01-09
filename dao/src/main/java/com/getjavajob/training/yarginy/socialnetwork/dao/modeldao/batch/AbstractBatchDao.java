package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.batch;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractBatchDao<E extends Entity> extends AbstractDao<E> implements BatchDao<E> {
    protected AbstractBatchDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                               String table, String tableAlias) {
        super(template, jdbcInsert, namedTemplate, table, tableAlias);
    }

    public boolean create(Collection<E> entities) {
        int size = entities.size();
        SqlParameterSource[] parametersList = new SqlParameterSource[size];
        int i = 0;
        for (E entity : entities) {
            parametersList[i++] = createEntityFieldsMap(entity);
        }
        try {
            return jdbcInsert.executeBatch(parametersList).length == size;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    public boolean delete(Collection<E> entities) {
        String query = getDeleteByAltKeysQuery();
        List<Object[]> keys = new ArrayList<>();
        for (E entity : entities) {
            keys.add(getObjectAltKeys(entity));
        }
        return template.batchUpdate(query, keys).length == entities.size();
    }

    public boolean update(Collection<E> entities, Collection<E> storedEntities) {
        Collection<E> entitiesToDelete = new ArrayList<>(storedEntities);
        entitiesToDelete.removeAll(entities);
        delete(entitiesToDelete);
        Collection<E> entitiesToCreate = new ArrayList<>(entities);
        entitiesToCreate.removeAll(storedEntities);
        create(entitiesToCreate);
        return true;
    }
}
