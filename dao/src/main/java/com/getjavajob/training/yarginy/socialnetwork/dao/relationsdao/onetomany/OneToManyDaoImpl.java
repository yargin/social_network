package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class OneToManyDaoImpl<M extends Entity, O extends Entity> implements OneToManyDao<O, M> {
    private final ConnectionPool connectionPool;
    private final OneToManyDml<O, M> oneToManyDml;

    public OneToManyDaoImpl(ConnectionPool connectionPool, OneToManyDml<O, M> oneToManyDml) {
        this.connectionPool = connectionPool;
        this.oneToManyDml = oneToManyDml;
    }

    @Override
    public Collection<M> selectMany(long oneId) {
        try (Connection connection = connectionPool.getConnection()) {
            return oneToManyDml.retrieveManyByOne(connection, oneId);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean relationExists(long manyId, long oneId) {
        try (Connection connection = connectionPool.getConnection()) {
            return oneToManyDml.retrieveByBoth(connection, oneId, manyId);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
