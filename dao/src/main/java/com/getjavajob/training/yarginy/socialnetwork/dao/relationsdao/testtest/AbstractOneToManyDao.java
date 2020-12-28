package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AbstractDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;

public abstract class AbstractOneToManyDao<O extends Entity, M extends Entity> {
    private final JdbcTemplate template;
    private final AbstractDao<O> oneDao;
    private final AbstractDao<M> manyDao;

    public AbstractOneToManyDao(DataSource dataSource, AbstractDao<O> oneDao, AbstractDao<M> manyDao) {
        template = new JdbcTemplate(dataSource);
        this.oneDao = oneDao;
        this.manyDao = manyDao;
    }

    public Collection<M> selectMany(long oneId) {
        String query = getSelectManyQuery();
        return template.query(query, manyDao.getRowMapper());
    }

    protected abstract String getSelectManyQuery();

    public boolean relationExists(long oneId, long manyId) {
        String query = getSelectOneQuery();
        return template.query(query, oneDao.getRowMapper()).size() > 1;
    }

    protected abstract String getSelectOneQuery();
}
