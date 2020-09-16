package com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class OneToManyDaoImpl<M extends Entity, O extends Entity> implements OneToManyDao<O, M> {
    private final ConnectionPool connectionPool;
    private final OneToManyDml<O, M> oneToManyDml;
    private final Dao<O> oneDao;
    private final Dao<M> manyDao;

    public OneToManyDaoImpl(ConnectionPool connectionPool, OneToManyDml<O, M> oneToManyDml, Dao<O> oneDao, Dao<M> manyDao) {
        this.connectionPool = connectionPool;
        this.oneToManyDml = oneToManyDml;
        this.oneDao = oneDao;
        this.manyDao = manyDao;
    }

    @Override
    public Collection<M> selectMany(O entity) {
        oneDao.checkEntity(entity);
        try (Connection connection = connectionPool.getConnection()) {
            return oneToManyDml.selectByOne(connection, entity.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public O selectOne(M entity) {
        manyDao.checkEntity(entity);
        try (Connection connection = connectionPool.getConnection()) {
            return oneToManyDml.selectByOneOfMany(connection, entity.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
