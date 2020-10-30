package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class OneToManyDaoImpl<M extends Entity, O extends Entity> implements OneToManyDao<O, M> {
    private final ConnectionPool connectionPool;
    private final OneToManyDml<O, M> oneToManyDml;
    private Dao<O> oneDao;

    public OneToManyDaoImpl(ConnectionPool connectionPool, OneToManyDml<O, M> oneToManyDml, Dao<O> oneDao) {
        this.connectionPool = connectionPool;
        this.oneToManyDml = oneToManyDml;
        this.oneDao = oneDao;
    }

    @Override
    public Collection<M> selectMany(O entity) {
        O storedEntity = oneDao.approveFromStorage(entity);
        try (Connection connection = connectionPool.getConnection()) {
            return oneToManyDml.selectByOne(connection, storedEntity);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
