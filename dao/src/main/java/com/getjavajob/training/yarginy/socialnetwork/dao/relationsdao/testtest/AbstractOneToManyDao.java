package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

public abstract class AbstractOneToManyDao<M extends Entity> implements Serializable {
    protected final transient JdbcTemplate template;

    public AbstractOneToManyDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public abstract Collection<M> selectMany(long oneId);

    public boolean relationExists(long oneId, long manyId) {
        String query = getSelectByBothQuery();
        return Objects.equals(template.queryForObject(query, getBothParams(oneId, manyId), getExitsRowMapper()), 1);
    }

    protected abstract String getSelectByBothQuery();

    protected abstract Object[] getBothParams(long oneId, long manyId);

    private RowMapper<Integer> getExitsRowMapper() {
        return (resultSet, i) -> resultSet.getInt(1);
    }
}
